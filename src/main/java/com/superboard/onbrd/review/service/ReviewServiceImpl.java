package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.boardgame.entity.BoardGame;
import com.superboard.onbrd.boardgame.service.BoardGameService;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.util.OciObjectStorageUtil;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameDetail;
import com.superboard.onbrd.review.dto.review.ReviewByFavoriteCountDetail;
import com.superboard.onbrd.review.dto.review.ReviewCreateDto;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewUpdateDto;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final BoardGameService boardGameService;
    private final OciObjectStorageUtil ociObjectStorageUtil;

    @Override
    @Transactional(readOnly = true)
    public OnbrdSliceResponse<AdminReviewDetail> getAdminReviews(OnbrdSliceRequest params) {
        return reviewRepository.getAdminReviews(params);
    }

    @Override
    @Transactional(readOnly = true)
    public OnbrdSliceResponse<ReviewByBoardgameDetail> getReviewsByBoardgameId(ReviewGetParameterDto params) {
        return reviewRepository.searchReviewsByBoardgameId(params);
    }

    @Override
    public Review crewateReview(ReviewCreateDto dto, List<String> images) {
        if (!images.isEmpty()) {
            List<String> imageList = processImages(images);
            dto.setImages(imageList);
        }

        Member writer = memberService.findVerifiedOneByEmail(dto.getEmail());
        BoardGame boardgame = boardGameService.findVerifiedOneById(dto.getBoardgameId());

        Review created = Review.builder()
                .writer(writer)
                .boardgame(boardgame)
                .grade(dto.getGrade())
                .content(dto.getContent())
                .images(dto.getImages())
                .build();

        writer.writeReview(created);

        return reviewRepository.save(created);
    }

    @Override
    public Review updateReview(ReviewUpdateDto dto, List<String> images) {
        Review updated = findVerifiedOneById(dto.getReviewId());
        if (!images.isEmpty()) {
            List<String> imageList = processImages(images);
            dto.setImages(imageList);
        }
        updated.updateGrade(dto.getGrade());
        updated.updateContent(dto.getContent());
        updated.updateImages(dto.getImages());

        return updated;
    }

    private List<String> processImages(List<String> images) {
        List<String> imageList = new ArrayList<>();
        String fileName = "[]";
        if (!images.isEmpty()) {
            for (String encodedImageName : images) {
                if (encodedImageName != null) {
                    byte[] decodedImageName = decodeImage(encodedImageName);
                    fileName = "review/" + saveImage(decodedImageName);
                    imageList.add(fileName);
                }
            }
        }
        return imageList;
    }

    private byte[] decodeImage(String encodedImageName) {
        if (encodedImageName.startsWith("data:")) {
            int base64Index = encodedImageName.indexOf("base64,");
            if (base64Index != -1) {
                encodedImageName = encodedImageName.substring(base64Index + "base64,".length());
            }
        }
        return Base64.getDecoder().decode(encodedImageName);
    }

    private String saveImage(byte[] decodedImageName) {
        String fileName = UUID.randomUUID().toString();
        String fileExtension = "png";
        String filePath = "review/";

        try {
            Path tempFile = Files.createTempFile(fileName, "." + fileExtension);
            FileUtils.writeByteArrayToFile(tempFile.toFile(), decodedImageName);
            MultipartFile multipartFile = createMultipartFile(tempFile);
            fileName = multipartFile.getOriginalFilename();
            ociObjectStorageUtil.UploadObject(multipartFile, filePath);
            tempFile.toFile().deleteOnExit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    private MultipartFile createMultipartFile(Path tempFile) throws IOException {
        File file = tempFile.toFile();
        String fileName = file.getName();
        String contentType = Files.probeContentType(tempFile);
        DiskFileItem fileItem = new DiskFileItem(fileName, contentType, false, fileName, (int) file.length(),
                file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);

        return new CommonsMultipartFile(fileItem);
    }

    @Override
    public Review hideReview(Long id) {
        Review review = findVerifiedOneById(id);
        review.hide();

        return review;
    }

    @Override
    public void deleteReviewById(Long id) {
        Review deleted = findVerifiedOneById(id);
        List<String> imageList = deleted.getImages();
        if (!CollectionUtils.isEmpty(imageList)) {
            String filePath = "";
            try {
                for (String image : imageList) {
                    boolean isObjectExists = ociObjectStorageUtil.getObjectOne(image, filePath);
                    if(isObjectExists){
                        ociObjectStorageUtil.deleteObject(imageList, filePath);
                    }
                }
            } catch (Exception e) {
                if (e.getMessage().contains("ObjectNotFound")) {
                    reviewRepository.delete(deleted);
                } else {
                    throw new BusinessLogicException(FILE_NOT_FOUND);
                }
            }
        }
        reviewRepository.delete(deleted);
    }

    @Override
    @Transactional(readOnly = true)
    public Review findVerifiedOneById(Long id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);

        return reviewOptional.orElseThrow(() -> {
            throw new BusinessLogicException(REVIEW_NOT_FOUND);
        });
    }

    @Override
    public OnbrdSliceResponse<ReviewByFavoriteCountDetail> selectRecommandReviewList(OnbrdSliceRequest request) {
        return reviewRepository.selectRecommandReviewList(request);
    }
}
