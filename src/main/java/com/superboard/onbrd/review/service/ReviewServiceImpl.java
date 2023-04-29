package com.superboard.onbrd.review.service;

import static com.superboard.onbrd.global.exception.ExceptionCode.*;
import static com.superboard.onbrd.member.entity.ActivityPoint.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.superboard.onbrd.admin.dto.AdminReviewDetail;
import com.superboard.onbrd.boardgame.entity.BoardGame;
import com.superboard.onbrd.boardgame.service.BoardGameService;
import com.superboard.onbrd.global.dto.OnbrdSliceRequest;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.global.entity.PageBasicEntity;
import com.superboard.onbrd.global.exception.BusinessLogicException;
import com.superboard.onbrd.global.util.OciObjectStorageUtil;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberLevel;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameDetail;
import com.superboard.onbrd.review.dto.review.ReviewByFavoriteCountDetail;
import com.superboard.onbrd.review.dto.review.ReviewCreateDto;
import com.superboard.onbrd.review.dto.review.ReviewGetParameterDto;
import com.superboard.onbrd.review.dto.review.ReviewUpdateDto;
import com.superboard.onbrd.review.entity.Review;
import com.superboard.onbrd.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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

	/* FIXME : 파일 업로드 수행 시 파일 생성하면서
		
	 */
	@Override
	public Review crewateReview(ReviewCreateDto dto,  List<String> images) {
		if(!images.isEmpty()){
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

		writer.increasePoint(REVIEW_WRITING.getPoint());
		writer.updateLevel(
			MemberLevel.getLevelCorrespondingPoint(writer.getPoint()));

		return reviewRepository.save(created);
	}

	@Override
	public Review updateReview(ReviewUpdateDto dto, List<String> images) {
		Review updated = findVerifiedOneById(dto.getReviewId());
		List<String> imageList = updated.getImages();
		String filePath = "review/";
		try {
			ociObjectStorageUtil.deleteObject(imageList, filePath);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (!images.isEmpty()) {
			List<String> updateImageList = processImages(images);
			dto.setImages(updateImageList);
		}
		updated.updateGrade(dto.getGrade());
		updated.updateContent(dto.getContent());
		updated.updateImages(dto.getImages());

		return updated;
	}

	private List<String> processImages(List<String> images) {
		List<String> imageList = new ArrayList<>();
		if (!images.isEmpty()) {
			for (String encodedImageName : images) {
				byte[] decodedImageName = decodeImage(encodedImageName);
				String fileName = saveImage(decodedImageName);
				imageList.add(fileName);
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
		String fileName = null;

		try {
			fileName =  UUID.randomUUID().toString();
			String fileExtension = "png";
			String filePath = "review/";
			Path tempFile = Files.createTempFile( fileName, "." + fileExtension);
			Files.write(tempFile, decodedImageName);

			MultipartFile multipartFile = createMultipartFile(tempFile);
			fileName = multipartFile.getOriginalFilename();

			ociObjectStorageUtil.UploadObject(multipartFile, filePath);

			Files.deleteIfExists(tempFile);

		} catch (IOException e) {
			throw new RuntimeException("Error saving image: " + e.getMessage(), e);

		} catch (Exception e) {
			throw new RuntimeException("Error saving image: " + e.getMessage(), e);
		}
		if (fileName == null) {
			throw new RuntimeException("Error saving image: file name is null");
		}
		return fileName;
	}

	private MultipartFile createMultipartFile(Path tempFile) throws IOException {
		File file = tempFile.toFile();
		if (!file.exists() || !file.canRead()) {
			throw new IOException("File does not exist or is not readable");
		}
		String fileName = file.getName();

		// Check if the file name is null or empty
		if (fileName.isEmpty()) {
			throw new IOException("File name is null or empty");
		}

		String contentType = Files.probeContentType(tempFile);
		DiskFileItem fileItem = new DiskFileItem(fileName, contentType, false, fileName, (int) file.length(), file.getParentFile());

		InputStream input = null;
		OutputStream os = null;
		try {
			input = new FileInputStream(file);
			os = fileItem.getOutputStream();
			IOUtils.copy(input, os);
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(os);
		}

		MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

		fileItem.delete();
		file.deleteOnExit();

		return multipartFile;
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
		if (!imageList.isEmpty()) {
			try {
				String filePath = "review/";
				ociObjectStorageUtil.deleteObject(imageList, filePath);
			} catch (Exception e) {
				e.printStackTrace();
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
	public OnbrdSliceResponse<ReviewByFavoriteCountDetail> selectRecommandReviewList(PageBasicEntity pageBasicEntity) {
		return reviewRepository.selectRecommandReviewList(pageBasicEntity);
	}
}
