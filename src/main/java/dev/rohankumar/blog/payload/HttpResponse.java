package dev.rohankumar.blog.payload;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpResponse {

    private int httpStatusCode;
    private String message;
    private boolean success;
}
