package toy.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STAUTS = "error";

    private String status;
    private String message;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> ApiResponse<T> successMessage(String message) {
        return new ApiResponse<>(SUCCESS_STATUS, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ERROR_STAUTS, message);
    }
}
