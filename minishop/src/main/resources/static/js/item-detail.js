$(function () {
    var waitVm = new Vue({
        el: "#waitTab",
        data: {
            bookDate: "",
            bookTime: "",
            hasErrorDate: false,
            hasErrorTime: false
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
                    contentType: "application/json;charset=UTF-8",
                    dataType:"json",
                    url: "http://localhost:8080/client/book",
                    data: JSON.stringify(appointmentParams),
                    success: function (res) {
                        if(res.code == "0"){
                            alert("You have booked an appointment successfully!");
                            waitVm.bookDate = "";
                            waitVm.bookTime = "";
                        }else{
                            alert(res.message);
                        }
                    },
                    error: function (res) {
                        var result = $.parseJSON(res.responseText);
                        if(result.status == 401){
                            window.location.href = "http://localhost:8080/client/login";
                        }
                    }
                });
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
        , max: [18,0]
        , interval: 15
    });

    // $('#btnBook').click(function () {
    //     var submitAbled = true;
    //     $('.app-error').remove();
    //
    //     if($('#bookDate').val() == ""){
    //         $('#bookDate').after("<span class='app-error'>The date is required.</span>");
    //         submitAbled = false;
    //     }
    //
    //     if($('#bookTime').val() == ""){
    //         $('#bookTime').after("<span class='app-error'>The time is required.</span>")
    //         submitAbled = false;
    //     }
    //
    //     return submitAbled;
    // });

    // $("#bookDate").change(function(){
    //     $('.app-error').remove();
    // });
    // $("#bookTime").change(function(){
    //     $('.app-error').remove();
    // });

    $('#btnAddCartItem').click(function () {

        if($('#quantity').val() == ""){
            alert("Please input the quantity");
            return;
        }

        $.ajax({
            type: "get",
            contentType: "application/json;charset=UTF-8",
            dataType:"json",
            url: "http://localhost:8080/client/cart-item?itemId=" + $('#itemId').val() + "&quantity=" + $('#quantity').val(),
            success: function (res) {
                if(res.code == "0"){
                    alert("This item has been added in your cart.");
                }else{
                    alert(res.message);
                }
            },
            error: function (res) {
                var result = $.parseJSON(res.responseText);
                if(result.status == 401){
                    window.location.href = "http://localhost:8080/client/login";
                }
            }
        });
    });

})

function validateAppointmentForm() {
    $('#appointmentForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            bookDate:{
                validators:{
                    notEmpty: {
                        message: 'The date is required'
                    }
                }
            },
            bookTime: {
                validators: {
                    notEmpty: {
                        message: 'The time is required'
                    }
                }
            }

        }

    });

}