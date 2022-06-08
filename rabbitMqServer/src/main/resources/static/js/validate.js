$(function(){
    $('#getButton').bind('click', getValidCode)
    $('#checkButton').bind('click', validValidCode)
})

// 获取验证码
function getValidCode(){
    var mail = $('#mail').val()
    $.ajax({
        url:"/sendMessage",
        type:"post",
        cache: false,
        data: {
            'phoneNumber': mail,
        },
        dataType: 'json',
        success:function(data){
            var success = data.success
            var message = data.message
            if(success){
                console.log(message)
                alert(message)
            }else{
                console.log(message)
                alert(message)
            }
        },
        error:function(){
            console.log("验证码获取错误")
        }
    })
}

// 验证验证码
function validValidCode(){
    var mail = $('#mail').val()
    var code = $('#code').val()
    $.ajax({
        url:"/validMessage",
        type:"post",
        cache: false,
        data: {
            'phoneNumber': mail,
            'code': code
        },
        dataType: 'json',
        success:function(data){
            var success = data.success
            var message = data.message
            if(success){
                console.log(message)
                alert(message)
            }else{
                console.log(message)
                alert(message)
            }
        },
        error:function(){
            console.log("验证错误")
        }
    })
}