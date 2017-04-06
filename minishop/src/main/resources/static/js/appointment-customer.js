$(function () {
    var vm = new Vue({
        el: "#appointment-customer",
        data: {
            selectedPeriod: "",
            appointments: [],
            appointmentForm: {id:"", bookDate:"", bookTime: ""}
        },
        created: function () {
            this.queryAppointments("0");
        },
        methods: {
            queryAppointments: function (period) {
                var paras = {period: period};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType:"json",
                    url: "/client/appointments",
                    data: paras,
                    success: function (res) {
                        if(res.code == "0"){
                            vm.appointments = res.rs;
                        }
                    }
                });
            },
            editItem: function (event) {
                for (var index in this.appointments){
                    if(this.appointments[index].id == event.target.id){
                        this.appointmentForm.id = this.appointments[index].id;
                        this.appointmentForm.bookDate = this.appointments[index].bookDate;
                        this.appointmentForm.bookTime = this.appointments[index].bookTime;

                        $('#bookDate').val(this.appointments[index].bookDate);
                        $('#bookTime').val(this.appointments[index].bookTime);

                        $('#editAppointmentModal').modal({
                            backdrop: 'static',
                            show: true
                        });

                        break;
                    }
                }
            },
            deleteItem: function (event) {
                if(confirm("Are you sure you want to delete this item?")){
                    var paras = {id : event.target.id};

                    $.ajax({
                        type: "post",
                        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                        dataType: "json",
                        url: "/client/appointments/delete",
                        data: paras,
                        success: function (res) {
                            if(res.code == 0){
                                vm.queryAppointments("0");
                            }
                        }
                    });
                }
            },
            updateItem: function () {
                this.appointmentForm.bookDate = $('#bookDate').val();
                this.appointmentForm.bookTime = $('#bookTime').val();

                $.ajax({
                    type: "post",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/client/appointments/update",
                    data: this.appointmentForm,
                    success: function (res) {
                        if(res.code == 0){
                            $('#editAppointmentModal').modal('hide');
                            vm.queryAppointments("0");
                        }
                    }
                });
            }
        }

    });


    $("#bookDate").datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1
    });

    $("#bookTime").datetimepicker({
        format: 'hh:ii',
        startView: 'day',
        minView: "hour",
        startDate: "09:00",
        endDate: "18:00",
        autoclose: 1,
        minuteStep: 30
    });

});