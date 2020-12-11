/*
var main 안에 function 선언 이유?
다른 js들과 겹치지 않기 위해
만약 다른 곳에서 같은 이름으로 var main = function() {} 들이 쓰인다면 나중에 불러온 js의 main function으로 덮어 씌워짐
*/
var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/post',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            location.reload();
        }).fail(function () {
            alert(error);
        });
    }
};

main.init();
