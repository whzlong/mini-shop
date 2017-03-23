$(function () {
    var vm = new Vue({
        el: "#item-detail-page",
        data: {
            bookDate: "",
            bookTime: "",
            hasErrorDate: false,
            hasErrorTime: false,
            brandId: "",
            brandName: "",
            item: {}
        },
        created: function () {

            $.ajax({
                type: "get",
                contentType: "application/x-www-form-urlencoded",
                dataType:"json",
                url: "/client/items/" + $('#itemId').val(),
                success: function (res) {
                    if(res.code == "0"){
                        vm.item = res.rs;

                        store.set('selectedBrand', vm.item.brandId);
                        store.set('selectedModel', vm.item.modelId);
                    }else{
                        alert(res.message);
                    }
                }
            });
        },
        methods: {
            bookAppointment: function (event){

                this.bookDate = $('#bookDate').val();

                if(this.bookDate == ""){
                    this.hasErrorDate = true;
                    alert("Please input the appointment date.");
                    return;
                }else{
                    this.hasErrorDate = false;
                }

                this.bookTime = $('#bookTime').val();
                if(this.bookTime == ""){
                    alert("Please input the appointment time.");
                    this.hasErrorTime = true;
                    return;
                }else {
                    this.hasErrorTime = false;
                }

                var appointmentParams = {itemId: $('#itemId').val(), bookDate: $('#bookDate').val(), bookTime: $('input[name=bookTime]').val()};

                $.ajax({
                    type: "post",
                    contentType: "application/x-www-form-urlencoded",
                    dataType:"json",
                    url: "/client/book",
                    data: appointmentParams,
                    success: function (res) {
                        if(res.code == "0"){
                            alert("You have booked an appointment successfully!");
                            vm.bookDate = "";
                            vm.bookTime = "";
                        }else{
                            alert(res.message);
                        }
                    },
                    error: function (res) {
                        var result = $.parseJSON(res.responseText);
                        if(result.status == 401){
                            window.location.href = "/client/login";
                        }
                    }
                });
            },
            addCartItem: function () {
                if(hasValidatorErrors()){
                    return;
                }

                $.ajax({
                    type: "post",
                    contentType: "application/x-www-form-urlencoded",
                    dataType:"json",
                    url: "/client/cart-items",
                    data: {itemId: $('#itemId').val(), quantity: $('#quantity').val()},
                    success: function (res) {
                        if(res.code == "0"){
                            alert("This item has been added in your cart.");
                            $('#quantity').val("");
                        }else{
                            alert(res.message);
                        }
                    },
                    error: function (res) {
                        var result = $.parseJSON(res.responseText);
                        if(result.status == 401){
                            window.location.href = "/client/login";
                        }
                    }
                });
            },
            jumpToSelectService: function () {
                window.location.href = "/client/select-service";
            }
        }
    });


    //设定选择日期
    $('#bookDate').pickadate({
                        format:'dd-mm-yyyy',
                        formatSubmit: 'yyyy/mm/dd',
                        hiddenName: true
    });

    //设定选择时间
    $('#bookTime').pickatime({formatSubmit: 'HH:i'
        , hiddenName: true
        , min: [9,00]
        , max: [18,00]
        , interval: 30
    });


    function hasValidatorErrors() {
        if($('#quantity').val() == ""){
            alert("Please input the quantity");
            return true;
        }

        var r = /^\+?[1-9][0-9]*$/;

        if(!r.test($('#quantity').val())){
            alert("Please input the positive integer");
            return true;
        }

        return false;

    }
});


