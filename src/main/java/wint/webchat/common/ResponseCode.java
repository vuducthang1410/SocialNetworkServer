package wint.webchat.common;

public enum ResponseCode {
    //SYSTEM - SYS
    SYSTEM("ERR_SYS_0001", "Lỗi hệ thống. Vui lòng thử lại sau!"),
    COOKIE_NOT_EXIST("ERR_SYS_0002","Cookie không có dữ liệu!"),

    //ACCOUNT - ACC
    ACCOUNT_ALREADY_EXISTS("ERR_ACC_0001", "Tên người dùng đã tồn tại"),
    ROLE_NOT_EXITS("ERR_ACC_0002", "Quyền người dùng không tồn tại"),
    DATA_REGISTER_ACCOUNT_MISSING("ERR_ACC_0003", "Thông tin đăng ký tài khoản chưa đầy đủ"),
    ACCOUNT_NOT_EXITS("ERR_ACC_0004", "Tài khoản người dùng không tồn tại"),
    PASSWORD_NOT_CORRECT("ERR_ACC_0005", "Mật khẩu chưa chính xác"),
    DATA_LOGIN_MISSING("ERR_ACC_0006", "Thông tin đăng nhập chưa đầy đủ"),
    REQUEST_DATA_MISSING("ERR_ACC_0007","Vui lòng nhập đầy đủ dữ liệu"),

    //TOKEN - TK
    REFRESH_TOKEN_COOKIE_MISSING("ERR_TK_0001", "Không tìm thấy refresh token trong cookie"),
    REFRESH_TOKEN_MISSING("ERR_TK_0002","Không tìm thấy refesh token"),
    TOKEN_NOT_ALLOW("ERR_TK_0003","Token không hợp lệ"),
    TOKEN_EXPIRATION("ERR_TK_0004","Token đã hết hạn"),
    TOKEN_RESET_PASSWORD_NOT_ALLOW("ERR_TK_0005","Token để thực hiện reset password không hợp lệ"),

    ;
    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
