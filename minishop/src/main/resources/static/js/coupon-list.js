$(function () {
   var vm = new Vue({
       el: "#coupon-list",
       data: {
           coupons: [],
           pagination: {startPage: 1, endPage: 5, currentPage: 1, totalPage: 0, pageSize: 10, size: 3},
           pageNums: [],
           selectedCoupons: [],
           selectedUsers: [],
           searchUsersConditions: {email:"", username: ""},
           searchConditionsOfEmail: "",
           searchConditionsOfUsername: "",
           testValue:"",
           searchConditions: {code: "", couponName:""},
           users: [],
           hasNotUsers: true,
           pageForm: {code: "", couponName: "", validDateFrom: "", validDateTo: "", amount: ""}
       },
       created: function () {
           this.query(1);
       },
       watch: {
           pageForm: function (newValue, oldValue) {
               validateForm(this.validateFields);
           },
           searchConditionsOfEmail: function (newValue) {
               this.queryUsers();
           },
           searchConditionsOfUsername: function (newValue) {
               this.queryUsers();
           }
       },
       methods: {
           query: function (page) {
               this.pagination.currentPage = page;

               this.searchConditions.code = $('#codeSearch').val();
               this.searchConditions.couponName = $('#couponNameSearch').val();

               var queryParas = {code: this.searchConditions.code
                                , couponName: this.searchConditions.couponName
                                , currentPage: this.pagination.currentPage
                                , size: this.pagination.size};

               $.ajax({
                   type: "get",
                   contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                   dataType:"json",
                   url: "http://localhost:8080/admin/coupon-list",
                   data: queryParas,
                   success: function (res) {
                       if(res.code == "0"){
                           vm.coupons = res.data;
                           vm.pagination.listCount = res.count;
                           setPagination();

                           if(res.count == 0){
                               alert("Relevant information is not available！");
                           }
                       }else{
                           alert(res.message);
                       }
                   }
               });
           },
           search: function () {
               this.query(1);
           },
           queryCurrentPage: function(event){
               this.query(parseInt(event.target.text));
           },
           previousPage: function () {
               vm.pagination.currentPage -= 1;

               this.query(vm.pagination.currentPage);

           },
           nextPage: function () {
               vm.pagination.currentPage += 1;

               this.query(vm.pagination.currentPage);
           },
           addItem: function () {
               clearFormElements(this.pageForm);
           },
           editItem: function (event) {
               for(var index in this.coupons){
                   if(event.target.id == this.coupons[index].code){
                       this.pageForm.code = this.coupons[index].code;
                       this.pageForm.couponName = this.coupons[index].couponName;
                       this.pageForm.validDateFrom = this.coupons[index].validDateFrom;
                       this.pageForm.validDateTo = this.coupons[index].validDateTo;
                       this.pageForm.amount = this.coupons[index].amount;
                   }
               }
           },
           saveItem: function () {
               if(validateForm(this.validateFields)){
                   return;
               }

               vm.pageForm.validDateFrom = $('#validDateFrom').val();
               vm.pageForm.validDateTo = $('#validDateTo').val();

               $.ajax({
                   type: "post",
                   dataType:"json",
                   url: "http://localhost:8080/admin/coupons",
                   contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                   data: vm.pageForm,
                   success: function (res) {
                       if(res.code == "0"){
                           $('#pageFormModal').modal('hide');
                           vm.query(vm.pagination.currentPage);
                       }else{
                           alert(res.message);
                       }
                   }
               });
           },
           openAssignPage: function () {
                this.hasNotUsers = false;

                this.queryUsers();
           },
           queryUsers: function () {
               var paras = {email: this.searchConditionsOfEmail, firstName: this.searchConditionsOfUsername, currentPage: 1, size: 10};

               $.ajax({
                   type: "get",
                   contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                   dataType:"json",
                   url: "http://localhost:8080/admin/user-list",
                   data: paras,
                   success: function (res) {
                       if(res.code == "0"){
                           vm.users = res.data;
                       }else{
                           alert(res.message);
                       }

                       if(vm.users.length == 0){
                           vm.hasNotUsers = true;
                       }else{
                           vm.hasNotUsers = false;
                       }
                   }
               });
           },
           clearSelectedUsers: function () {
                this.selectedUsers = [];
           },
           assign: function () {

               if(vm.selectedCoupons.length == 0){
                   alert("Please select the coupons");
                   return;
               }

               if(vm.selectedUsers.length == 0){
                   alert("Please select the users");
                   return;
               }

               if(vm.selectedUsers.length > 0 && vm.selectedCoupons.length > 0){
                   var paras = {assignedUsers: vm.selectedUsers, coupons: vm.selectedCoupons};

                   $.ajax({
                       type: "post",
                       dataType:"json",
                       url: "http://localhost:8080/admin/assign-coupons",
                       contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                       data: paras,
                       success: function (res) {
                           if(res.code == "0"){
                               $('#pageFormModal').modal('hide');
                               vm.query(vm.pagination.currentPage);
                           }else{
                               alert(res.message);
                           }

                           vm.selectedUsers = [];
                           vm.selectedCoupons = [];

                           $('#assignFormModal').modal('hide');
                       }
                   });
               }
           }

       }
   });

    vm.validateFields = {couponName:
                            {validators:
                                {
                                    notEmpty: {
                                        message: 'The name is required'
                                    }
                                }
                            }
                            , validDateFrom:
                                {validators:
                                    {
                                        notEmpty: {
                                            message: 'The valid date(From) is required'
                                        }
                                    }
                                }
                            , validDateTo:
                                {validators:
                                    {
                                        notEmpty: {
                                            message: 'The valid date(To) is required'
                                        }
                                    }
                                }
                            , amount:
                                {validators:
                                    {
                                        notEmpty: {
                                            message: 'The amount is required'
                                        }
                                    }
                                }
                        };


    function validateForm(validateFields) {
        var ret = false;

        for(var fieldName in validateFields){

            var validators = validateFields[fieldName].validators;

            for(var validatorName in validators){
                if(validatorName == "notEmpty"){
                    if($('#' + fieldName).val() == null || $('#' + fieldName).val() == ""){
                        $('#'+fieldName + ' ~ .error-message').remove();
                        $('#'+fieldName).after("<span class='error-message'>"+ validators[validatorName].message +"</span>");
                        ret = true;
                    }else{
                        $('#'+fieldName + ' ~ .error-message').remove();
                    }
                }
            }


        }

        return ret;
    }


    function setPagination() {

        if(vm.pagination.listCount % vm.pagination.size == 0){
            vm.pagination.totalPage  = vm.pagination.listCount / vm.pagination.size;
        }else{
            vm.pagination.totalPage  = parseInt(vm.pagination.listCount / vm.pagination.size) + 1;
        }

        if(vm.pagination.currentPage > vm.pagination.endPage){
            vm.pagination.startPage = vm.pagination.currentPage;

            if(vm.pagination.totalPage - vm.pagination.currentPage + 1 >= vm.pagination.pageSize){
                vm.pagination.endPage = vm.pagination.currentPage + vm.pagination.pageSize -1;
            }else{
                vm.pagination.endPage = vm.pagination.totalPage;
            }

        }else if(vm.pagination.currentPage < vm.pagination.startPage){
            vm.pagination.endPage = vm.pagination.currentPage;
            vm.pagination.startPage = vm.pagination.endPage - vm.pagination.pageSize + 1;
        }else{
            if(vm.pagination.totalPage > vm.pagination.pageSize){
                vm.pagination.endPage = vm.pagination.pageSize;
            }else{
                vm.pagination.endPage = vm.pagination.totalPage;
            }
        }

        vm.pageNums = [];
        for(var i = 0; i < (vm.pagination.endPage - vm.pagination.startPage + 1); i++){
            vm.pageNums.push(i + vm.pagination.startPage);
        }
    }

    function clearFormElements(form) {
        for(var index in form){
            form[index] = "";
        }
    }

    $( "#validDateFrom" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1
    });

    $( "#validDateTo" ).datetimepicker({
        format: 'dd-mm-yyyy',
        minView: "month",
        autoclose: 1
    });
});
