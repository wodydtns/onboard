insert into BADGE ("ID", "BADGE_TYPE")
select 1, 'JOIN'
from DUAL
union all
select 2, 'POST_FIRST_REVIEW'
from DUAL
union all
select 3, 'POST_FIVE_REVIEWS'
from DUAL
union all
select 4, 'ATTEND_FIVE_DAYS'
from DUAL
union all
select 5, 'ATTEND_THIRTY_DAYS'
from DUAL
union all
select 6, 'SET_PROFILE_CHARACTER'
from DUAL
union all
select 7, 'PUSH_ALARM_ON'
from DUAL
union all
select 8, 'POST_FIVE_COMMENTS'
from DUAL
union all
select 9, 'SELECTED_RECOMMENDED_REVIEW'
from DUAL
union all
select 10, 'GAIN_TEN_REVIEW_LIKES'
from DUAL;
