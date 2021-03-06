/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["form", "upload"], function (t) {
    var i = layui.$, e = layui.layer, a = (layui.laytpl, layui.setter, layui.view, layui.admin), n = layui.form,
        s = layui.upload;
    i("body");
    n.verify({
        nickname: function (t, i) {
            return new RegExp("^[a-zA-Z0-9_一-龥\\s·]+$").test(t) ? /(^\_)|(\__)|(\_+$)/.test(t) ? "用户名首尾不能出现下划线'_'" : /^\d+\d+\d$/.test(t) ? "用户名不能全为数字" : void 0 : "用户名不能有特殊字符"
        },
        usertype: [/^[1-5]$/, "请选择"],
        pass: [/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/, "密码必须6到12位，包含字母且区分大小写不能出现空格"],
        repass: function (t) {
            if (t !== i("#LAY_password").val()) return "两次密码输入不一致"
        }
    }), n.on("submit(set_website)", function (t) {
        return e.msg(JSON.stringify(t.field)), !1
    }), n.on("submit(setmypass)", function (t) {
        i.ajax({
            url: "/user",
            type: "POST",
            dataType: "json",
            data: t.field,
            success: function (res) {
                res.code !== 1 ?
                    e.msg(res.msg, {icon: 5})
                    :
                    e.msg(res.msg, {icon: 1}, function () {
                        location.reload();
                    })
            }
        })
        return !1
    }), n.on("submit(setmyinfo)", function (t) {
        i.ajax({
            url: "/user/info",
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(t.field),
            success: function (res) {
                res.code !== 1 ?
                    e.msg(res.msg, {icon: 5})
                    :
                    e.msg(res.msg, {icon: 1}, function () {
                        location.reload();
                    })
            }
        })
        return !1
    });
    var r = i("#LAY_avatarSrc");
    s.render({
        url: "/upload/avatar", elem: "#LAY_avatarUpload", done: function (t) {
            1 === t.code ? (e.msg(t.msg, {icon: 1}), r.val(t.data)) : e.msg(t.msg, {icon: 5})
            return false;
        }
    }), a.events.avartatPreview = function (t) {
        var i = r.val();
        e.photos({photos: {title: "查看头像", data: [{src: i}]}, shade: .01, closeBtn: 1, anim: 5})
        return false;
    }, t("set", {})
});