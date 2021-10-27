package orz.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import orz.zerock.guestbook.dto.GuestbookDTO;
import orz.zerock.guestbook.dto.PageRequestDTO;
import orz.zerock.guestbook.dto.PageResultDTO;
import orz.zerock.guestbook.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().
                page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.
                getList(pageRequestDTO);
        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: "+ resultDTO.getTotalPage());
        System.out.println("----------------------------------");
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
        System.out.println("====================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    //서비스 영역에서 검색조건을 처리할수 있도록 구성 //테스트 코드
    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc") //검색 조건 t,c,w,tc,tcw..
                .keyword("1") //검색 키워드
                .build();
        PageResultDTO<GuestbookDTO, Guestbook> requestDTO = service.
                getList(pageRequestDTO);

        System.out.println("PREV: "+requestDTO.isPrev());
        System.out.println("NEXT: "+requestDTO.isNext());
        System.out.println("TOTAL: "+requestDTO.getTotalPage());
        System.out.println("---------------------------");
        for (GuestbookDTO guestbookDTO : requestDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("===================================");
        requestDTO.getDtoList().forEach(i -> System.out.println(i));
    }
}
