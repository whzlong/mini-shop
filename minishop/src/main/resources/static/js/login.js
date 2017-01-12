function validateRegisterForm() {
    $('#registerForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            firstName:{
                validators:{
                    notEmpty: {
                        message: 'The first name is required'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'The email is required'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The password must be more than 6 and less than 30 characters long'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'The password and its confirm are not the same'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirmPassword is required'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        message: 'The password must be more than 6 and less than 30 characters long'
                    },
                    identical: {
                        field: 'password',
                        message: 'The password and its confirm are not the same'
                    }
                }
            }

        }

    }).on('success.form.bv', function(e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            //var bv = $form.data('bootstrapValidator');

            //Use Ajax to submit form data
            $.post($form.attr('action')
                , $form.serialize()
                , function(result) {
                        if(result.code == "0"){
                            // Enable the submit buttons
                            $('#registerForm').bootstrapValidator('disableSubmitButtons', false);

                            $("#registerForm").data('bootstrapValidator').resetForm(true);
                            $("#registerErrorMsg").val("");
                            $("#lastName").val("");

                            $form.find('.alert').html("An email has been sent to you,please check your email to validate your registration informaiton!").show();

                        }else{
                            $('#registerErrorMsg').html(result.message);
                            // Enable the submit buttons
                            $('#registerForm').bootstrapValidator('disableSubmitButtons', false);
                        }
            }, 'json');

        });
}

function validateLoginForm() {
    $('#loginForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                email: {
                    validators: {
                        notEmpty: {
                            message: 'The email is required'
                        },
                        emailAddress: {
                            message: 'The input is not a valid email address'
                        }
                    }
                },
                password: {
                    validators:{
                        notEmpty: {
                            message: 'The username is required'
                        }
                    }
                }
            }
        }

    );
}