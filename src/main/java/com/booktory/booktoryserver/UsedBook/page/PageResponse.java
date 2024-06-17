package com.booktory.booktoryserver.UsedBook.page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponse<E> {

    private int pageNo;//페이지 번호
    private int size;  //페이지의 건수
    private int total; //전체 건수

    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;
    //다음 페이지의 존재 여부
    private boolean next;

    private List<E> list;

    @Builder(builderMethodName = "withAll")
    public PageResponse(List<E> list, int total, int pageNo, int size){
        // 데이터 세팅하고
        this.pageNo = pageNo;
        this.size = size;

        this.total = total;
        this.list = list;

        // 계산!
        // 여기서 원래 10으로 되어있었는데 size로 변경해줘야함!!!
        this.end =   (int)(Math.ceil(this.pageNo / 10.0 )) * 10;

        this.start = this.end - 9;

        // (double)size -> 10.0으로 바꾸는 건가
        int last =  (int)(Math.ceil((total/(double)size)));

        this.end =  end > last ? last: end;

        // 이전 버튼, 다음 버튼 나오는 조건
        this.prev = this.start > 1;

        this.next =  total > this.end * this.size;

    }
}
