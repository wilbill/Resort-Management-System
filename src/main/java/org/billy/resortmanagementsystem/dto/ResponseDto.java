package miu.edu.resort.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
    private int respCode;
    private String respMsg;
}
