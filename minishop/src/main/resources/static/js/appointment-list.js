$(function () {
    var vm = new Vue({
        el: '#appointmentList',
        data: {
            appointments: [],
            pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, pageSize: 10, size: 10},
            pageNums: [],
            bookDate: "",
            bookTime: "",
            state: "",
            appointmentId:"",
            comment:""
        },
        created: function () {
            var queryParas = {bookDateFrom: $('#bookDateFrom').val(), bookDateTo: $('#bookDateTo').val(), state: $('#stateQuery').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

            $.ajax({
                type: "get",
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                dataType:"json",
                url: "/admin/appointment-list",
                data: queryParas,
                success: function (res) {
                    vm.appointments = res.data;

                    setPagination(res.count);
                }
            });
        },
        methods: {
            query: function (page) {
                this.pagination.currentPage = page;

                var queryParas = {bookDateFrom: $('#bookDateFrom').val(), bookDateTo: $('#bookDateTo').val(), state: $('#stateQuery').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType:"json",
                    url: "/admin/appointment-list",
                    data: queryParas,
                    success: function (res) {
                        vm.appointments = res.data;

                        vm.pagination.currentPage = res.page + 1;

                        setPagination(res.count);

                        if(res.count == 0){
                            alert("Relevant information is not available！");
                        }

                    }
                });
            },
            search: function () {
                this.query(1);
            },
            queryCurrentPage: function(event){
                this.pagination.currentPage = parseInt(event.target.text);

                var queryParas = {bookDateFrom: $('#bookDateFrom').val(), bookDateTo: $('#bookDateTo').val(), state: $('#stateQuery').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType:"json",
                    url: "/admin/appointment-list",
                    data: queryParas,
                    success: function (res) {
                        vm.appointments = res.data;

                        setPagination(res.count);
                    }
                });
            },
            previousPage: function () {
                vm.pagination.currentPage -= 1;

                if(vm.pagination.currentPage < vm.pagination.startPage){
                    vm.pagination.endPage = vm.pagination.currentPage;
                    vm.pagination.startPage = vm.pagination.endPage - vm.pagination.pageSize + 1;

                    setPageNums();
                }

                var queryParas = {bookDateFrom: $('#bookDateFrom').val(), bookDateTo: $('#bookDateTo').val(), state: $('#stateQuery').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/admin/appointment-list",
                    data: queryParas,
                    success: function (res) {
                        vm.appointments = res.data;
                    }
                });
            },
            nextPage: function () {
                vm.pagination.currentPage += 1;

                if(vm.pagination.currentPage > vm.pagination.endPage){
                    vm.pagination.startPage = vm.pagination.currentPage;

                    if(vm.pagination.totalPage - vm.pagination.currentPage + 1 >= vm.pagination.pageSize){
                        vm.pagination.endPage = vm.pagination.currentPage + vm.pagination.pageSize -1;
                    }else{
                        vm.pagination.endPage = vm.pagination.totalPage;
                    }

                    setPageNums();
                }

                var queryParas = {bookDateFrom: $('#bookDateFrom').val(), bookDateTo: $('#bookDateTo').val(), state: $('#stateQuery').val(), currentPage: this.pagination.currentPage, size: this.pagination.size};

                $.ajax({
                    type: "get",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/admin/appointment-list",
                    data: queryParas,
                    success: function (res) {
                        vm.appointments = res.data;
                    }
                });

            },
            editItem: function (event) {

                for (var index in this.appointments){

                    if(this.appointments[index].id == event.target.id){
                        this.appointmentId = this.appointments[index].id;

                        this.bookDate = this.appointments[index].bookDate;
                        this.bookTime = this.appointments[index].bookTime;

                        $('#bookDate').val(this.appointments[index].bookDate);
                        $('#bookTime').val(this.appointments[index].bookTime);

                        this.state = this.appointments[index].state;
                        this.comment = this.appointments[index].comment;

                        $('#editAppointmentModal').modal({
                            backdrop: 'static',
                            show: true
                        });

                        break;
                    }
                }

            },
            updateItem: function (event) {
                this.bookDate = $('#bookDate').val();
                this.bookTime = $('#bookTime').val();

                var paras = {id: this.appointmentId, bookDate: this.bookDate, bookTime: this.bookTime, state: this.state, comment: this.comment};

                $.ajax({
                    type: "post",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/admin/appointments",
                    data: paras,
                    success: function (res) {
                        if(res.code == 0){
                            $('#editAppointmentModal').modal('hide');
                            vm.query(vm.pagination.currentPage);

                            clearEditContent();
                        }
                    }
                });
            },
            confirmWithCustomer: function (event) {
                var paras = {id: this.appointmentId, bookDate: this.bookDate, bookTime: this.bookTime, state: this.state, comment: this.comment};

                $.ajax({
                    type: "post",
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    dataType: "json",
                    url: "/admin/appointments/confirm",
                    data: paras,
                    success: function (res) {
                        if(res.code == 0){
                            $('#editAppointmentModal').modal('hide');
                            vm.query(vm.pagination.currentPage);

                            clearEditContent();
                        }
                    }
                });

            },
            deleteItem: function (event) {

                if(confirm("Are you sure you want to delete this item?")){
                    var paras = {id : event.target.id};

                    $.ajax({
                        type: "post",
                        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                        dataType: "json",
                        url: "/admin/appointments/delete",
                        data: paras,
                        success: function (res) {
                            if(res.code == 0){
                                vm.query(vm.pagination.currentPage);
                            }
                        }
                    });
                }

            }
        }
    });

    function setPagination(recordCount) {

        var totalPage = 0;

        if(recordCount % vm.pagination.size == 0){
            totalPage = recordCount / vm.pagination.size;
        }else{
            totalPage = parseInt(recordCount / vm.pagination.size) + 1;
        }

        vm.pagination.totalPage = totalPage;

        vm.pagination.startPage = 1;

        if(totalPage > vm.pagination.pageSize){
            vm.pagination.endPage = vm.pagination.pageSize;
        }else{
            vm.pagination.endPage = totalPage;
        }

        setPageNums();
    }
    
    function setPageNums() {
        vm.pageNums = [];
        for(var i = 0; i < (vm.pagination.endPage - vm.pagination.startPage + 1); i++){
            vm.pageNums.push(i + vm.pagination.startPage);
        }
    }

    function clearEditContent() {
        vm.appointmentId = "";
        vm.bookDate = "";
        vm.bookTime = "";
        vm.state = "";
        vm.comment = "";
    }

    $( "#bookDateQuery" ).daterangepicker(
        {
            locale:{format: 'DD-MM-YYYY'}
        }
    );

    $('#bookDateQuery').on('apply.daterangepicker', function(ev, picker) {
        $('#bookDateFrom').val(picker.startDate.format('DD-MM-YYYY'));
        $('#bookDateTo').val(picker.endDate.format('DD-MM-YYYY'));
    });

    //
    // $( "#bookDateQuery" ).datetimepicker({
    //     format: 'dd-mm-yyyy',
    //     minView: "month",
    //     autoclose: 1,
    //     clearBtn:true
    // });

    $( "#bookDate" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1

    });

    $( "#bookTime" ).datetimepicker({
        format: 'hh:ii',
        startView: 'day',
        minView: "hour",
        startDate: "09:00",
        endDate: "18:00",
        autoclose: 1,
        minuteStep: 30
    });

});


