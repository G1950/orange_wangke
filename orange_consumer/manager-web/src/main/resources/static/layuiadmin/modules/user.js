/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define("form", function (e) {
    var s = layui.$, t = (layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin), i = layui.form,
        a = s("body");
    i.verify({
        nickname: function (e, s) {
            return new RegExp("^[a-zA-Z0-9_一-龥\\s·]+$").test(e) ? /(^\_)|(\__)|(\_+$)/.test(e) ? "用户名首尾不能出现下划线'_'" : /^\d+\d+\d$/.test(e) ? "用户名不能全为数字" : void 0 : "用户名不能有特殊字符"
        }, pass: [/^(?=.*[0-9])(?=.*[a-zA-Z])(.{6,12})$/, '密码6-12位必须包含数字字母区分大小写']
    }), t.sendAuthCode({
        elem: "#LAY-user-getsmscode",
        elemEmail: "#LAY-user-login-email",
        elemVercode: "#LAY-user-login-vercode",
        ajax: {url: layui.setter.base + "json/user/sms.js"}
    }), a.on("click", "#LAY-user-get-vercode", function () {
        s(this);
        this.src = "https://www.oschina.net/action/user/captcha?t=" + (new Date).getTime()
    }), e("user", {})
});