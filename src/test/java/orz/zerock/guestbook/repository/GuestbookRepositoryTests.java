package orz.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import orz.zerock.guestbook.entity.Guestbook;
import orz.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        //존재하는 번호로 테스트
        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title...."); //300번이 바뀜
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }
    //단일 항목 검색 테스트
    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").ascending());
//                descending());
        QGuestbook qGuestbook = QGuestbook.guestbook; //1
//        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder(); //2
//        BooleanExpression expression = qGuestbook.title.contains(keyword); //3 (10개 출력)
//        BooleanExpression expression = qGuestbook.gno.between(100,200); //3
        BooleanExpression expression = qGuestbook.gno.gt(299); //3  300번 보다 크다
        builder.and(expression); //4
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); //5
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
    // 다중 항목 검색 테스트
    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}