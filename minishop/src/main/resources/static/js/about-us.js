$(function () {
    var vm = new Vue({
        el: '#about-us-page',
        data: {
            company: {}
        },
        created: function () {
            $.ajax({
                type: "get",
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                dataType:"json",
                url: "/client/company",
                success: function (res) {
                    vm.company = res.rs;
                }
            });
        }
    });
});