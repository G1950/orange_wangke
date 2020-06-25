package com.guangming.pojo;

public class OauthClientDetails {
    //客户端id
    private String client_id;
    //资源id集合
    private String resource_ids;
    //客户端密钥
    private String client_secret;
    //指定client的权限范围，比如读写权限，比如移动端还是web端权限
    private String scope;
    //授权方式
    private String authorized_grant_types;
    //客户端重定向uri，authorization_code和implicit需要该值进行校验
    private String web_server_redirect_uri;
    //权限
    private String authorities;
    //token时长
    private Integer access_token_validity;
    //refresh_token时长
    private Integer refresh_token_validity;
    //通用信息，须为json格式
    private String additional_information;
    //默认false,适用于authorization_code模式,设置用户是否自动approval操作,设置true跳过用户确认授权操作页面，直接跳到redirect_uri
    private String autoapprove;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id == null ? null : client_id.trim();
    }

    public String getResource_ids() {
        return resource_ids;
    }

    public void setResource_ids(String resource_ids) {
        this.resource_ids = resource_ids == null ? null : resource_ids.trim();
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret == null ? null : client_secret.trim();
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public String getAuthorized_grant_types() {
        return authorized_grant_types;
    }

    public void setAuthorized_grant_types(String authorized_grant_types) {
        this.authorized_grant_types = authorized_grant_types == null ? null : authorized_grant_types.trim();
    }

    public String getWeb_server_redirect_uri() {
        return web_server_redirect_uri;
    }

    public void setWeb_server_redirect_uri(String web_server_redirect_uri) {
        this.web_server_redirect_uri = web_server_redirect_uri == null ? null : web_server_redirect_uri.trim();
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities == null ? null : authorities.trim();
    }

    public Integer getAccess_token_validity() {
        return access_token_validity;
    }

    public void setAccess_token_validity(Integer access_token_validity) {
        this.access_token_validity = access_token_validity;
    }

    public Integer getRefresh_token_validity() {
        return refresh_token_validity;
    }

    public void setRefresh_token_validity(Integer refresh_token_validity) {
        this.refresh_token_validity = refresh_token_validity;
    }

    public String getAdditional_information() {
        return additional_information;
    }

    public void setAdditional_information(String additional_information) {
        this.additional_information = additional_information == null ? null : additional_information.trim();
    }

    public String getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove == null ? null : autoapprove.trim();
    }
}