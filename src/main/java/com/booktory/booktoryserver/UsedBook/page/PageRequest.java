package com.booktory.booktoryserver.UsedBook.page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

    @Builder.Default
    @Min(value = 1)
    @Positive
    private int pageNo = 1;

    @Builder.Default
    @Min(value = 8)
    @Max(value = 100)
    @Positive
    private int size = 8;

    private String link;

    private String searchKey;
    public int getSkip(){
        return (pageNo - 1) * size;
    }

    public String getLink() {
        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.pageNo);
            builder.append("&size=" + this.size);

            // 검색어가 존재할 경우
            if (this.searchKey != null && this.searchKey.length() != 0) {
                try {
                    builder.append("&searchKey=" + URLEncoder.encode(this.searchKey,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            link = builder.toString();
        }
        return link;
    }
}
