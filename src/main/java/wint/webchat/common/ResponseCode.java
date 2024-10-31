package wint.webchat.common;

public enum ResponseCode {
    //SYSTEM - SYS
    SYSTEM("ERR_SYS_0001", "Lỗi hệ thống. Vui lòng thử lại sau!"),

    //ACCOUNT - ACC
    ACCOUNT_ALREADY_EXISTS("ERR_ACC_0001","Tên người dùng đã tồn tại"),
    ROLE_NOT_EXITS("ERR_ACC_0002","Quyền người dùng không tồn tại"),
    DATA_REGISTER_ACCOUNT_MISSING("ERR_ACC_003","Thông tin đăng ký tài khoản chưa đầy đủ"),
    ACCOUNT_NOT_EXITS("ERR_ACC_004","Tài khoản người dùng không tồn tại"),
    PASSWORD_NOT_CORRECT("ERR_ACC_005","Mật khẩu chưa chính xác"),

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
