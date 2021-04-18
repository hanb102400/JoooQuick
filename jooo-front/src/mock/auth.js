Mock.mock("/ota/list?page=1", "get", (req,resp) =>{
    resp.status = 401
    return {
        code:40001,
        isLogin:false,
        msg:"授权失败"
    }
})