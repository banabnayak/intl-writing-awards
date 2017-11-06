angular.module('SWAApp.controllers', []).
        /* SWA controller */
        controller('SWAController', function ($scope, $rootScope, $window, $location, dialogAPIService, swaAPIService, $timeout) {
            //enrollment of a student	
            $scope.classes = [];
            function loadGroups() {
                swaAPIService.getGroups().then(
                        function (result, status, headers, config) {
                            var groups = result.data.group;
                            $.each(groups, function (i, o) {
                                var classes = groups[i].classes;
                                $.each(o.classes, function (j, c) {
                                    $scope.classes.push({id: c.id, text: c.name, group: {id: o.id, text: o.name}});
                                });
                            });
                        },
                        function (result, status, headers, config) {
                        }
                );
            }

            $scope.schools = [];
            function loadSchools() {
                swaAPIService.allSchools().then(
                        function (result, status, headers, config) {
                            $scope.schools = result.data.school;
                        },
                        function (data, status, headers, config) {
                        }
                );
            }

            loadGroups();
            loadSchools();
            $scope.aboutSWA = aboutSWA;
            $scope.user = null;
            $scope.refother = null;
            $scope.user = {
                student: {fullName: '', parentName: '', parentPhone: '', parentEmail: '', age: '', studentClass: '', studentGroup: '',refferalSources: ''},
                school: {name: '', address: '', city: '', state: '', pincode: '', principalName: '', principalEmail: '', phone: ''}
            };
            $scope.setGroup = function (cls) {
                $scope.user.student.studentGroup = cls.group.id;
                $scope.user.student.studentClass = cls.id;
                $scope.studentGroup = cls.group.text;
            };
            $scope.setSchoolName = function () {
                $scope.user.school.name = $scope.selectedSchool;
            };
            $scope.filterSchools = function (schoolName) {
                swaAPIService.filterSchools(schoolName).then(
                        function (result, status, headers, config) {
                            $.each(result.data.school, function (i, o) {
                                $scope.schools.push(o);
                            });
                        },
                        function (result, status, headers, config) {
                        }
                );
            };

            $scope.allsources = ALLSOURCES;
            $scope.isRefOtherSelected = false;

            $scope.selectOtherSource = function () {
                if ($scope.user.student.refferalSources === 'Others') {
                    $scope.isRefOtherSelected = true;
                }
                else {
                    $scope.isRefOtherSelected = false;
                }
                $scope.refother = "";

            };

            $scope.states = [];
            swaAPIService.getState().then(
                    function (result) {
                        $scope.states = result.data.stateList;
                    },
                    function (result) {
                        console.log(result);
                    }
            );
            $scope.cities = [];
            $scope.getCities = function () {
                var state = $scope.user.school.state, i;
                $scope.cities = [];
                if (state == "") {
                    $scope.cities = [];
                }
                else {
                    for (i = 0; i < $scope.states.length; i++) {
                        if ($scope.states[i].stateName.toLowerCase() === state) {
                            $scope.cities = $scope.states[i].cityList;
                            break;
                        }
                    }

                }
                $scope.user.school.city = "";
            }
            $scope.selectedSchool = null;
            $scope.onSelectPart = function (item, model, lebel) {
                item.students = null;
                $scope.user.school = angular.copy(item);
                $scope.selectedSchool = item.name;
                $scope.user.school.name = $scope.selectedSchool;
                var state = item.state.toLowerCase();
                if ($scope.states.containsObjVal(state, 'stateName')) {
                    $scope.user.school.state = state;
                    $scope.getCities();
                    var city = item.city.toLowerCase();
                    if (Array.isArray($scope.cities) && $scope.cities.containsObjVal(city, 'cityName')) {
                        $timeout(function () {
                            $scope.user.school.city = city;
                        }, 1000);
                    } else {
                        $scope.user.school.city = '';
                    }
                } else {
                    $scope.user.school.state = '';
                    $scope.user.school.city = '';
                }



            };
            $scope.isSubmitted = false;
            $scope.enrollStudent = function () {
                var user = $scope.user;
                var content = '<p id="msgDiv">' + MESSAGES.ENROLLMENT_CONFIRMATION + '</p>';
                dialogAPIService.createDialog(DIALOGS.ENROLLMENT.TEMPLATE, {
                    id: 'confDialog',
                    title: DIALOGS.ENROLLMENT.TITLE,
                    top: 150,
                    height: 350,
                    width: 400,
                    type: 'confirm',
                    text: content,
                    backdrop: true,
                    close: {label: 'Close', fn: function () {
                            cancel2Submit();
                        }},
                    success: {label: 'Confirm', hide: false, fn: function () {
                            confirm2Submit(user);
                        }}
                });
            };
            $scope.alerts = [];
            function confirm2Submit(user) {
                user.school.id = null;
                user.school.state.capitalize();
                user.school.city.capitalize();
                if ($scope.isRefOtherSelected) {
                    user.student.refferalSources = $scope.refother;
                }
                swaAPIService.submitEnrollmentForm(user).then(
                        function (result, status, headers, config) {
                            try {
                                $('#dBtn button:nth-child(2)').remove();
                                if (result.data == 'SUCCESS') {
                                    $('#msgDiv').html('<p style="text-align:center;color:blue">' + MESSAGES.ENROLLMENT_SUCCESS + '</p>');
                                    $location.path('/login');
                                } else {
                                    $('#msgDiv').html('<p style="text-align:center;color:blue">' + result.data + '</p>');
                                }
                            } catch (e) {
                            }
                        }, function (result, status, headers, config) {
                    alert(MESSAGES.ENROLLMENT_FAILED);
                });
            }

            function cancel2Submit() {
                $('#confDialog').remove();
            }
        }).controller('LoginController', function ($scope, $rootScope, $window, $location, authAPIService, dialogAPIService, swaAPIService) {
    /*login/logout move to Login controller */
    $scope.info = '';
    $scope.authuser = {
        user: {id: '', fullName: '', userId: '', password: '', role: '', status: ''},
        isStorySubmitted: false,
        isReviewStarted: false,
        rememberMe: false
    };
    try {
        var rememberMe = $.cookie('rememberMe');
        if (rememberMe != null && rememberMe == 'true') {
            $scope.authuser.user.userId = $.cookie('userId');
            $scope.authuser.rememberMe = true;
        } else {
            $scope.authuser.rememberMe = false;
        }
    } catch (e) {
    }
    $scope.info = '';
    $scope.login = function () {
        var user = $scope.authuser;
        authAPIService.login(user).then(
                function (result, status, headers, config) {
                    var userInfo = authAPIService.getUserInfo();
                    if (userInfo.status != 200) {
                        $scope.info = MESSAGES.AUTH_FAILED;
                    } else {
                        var data = result.data;
                        if (data.rememberMe) {
                            var date = new Date();
                            date.setTime(date.getTime() + (COOKIEAGE * 24 * 60 * 60 * 1000));
                            $.cookie('userId', data.user.userId, {expires: date});
                            $.cookie('rememberMe', data.rememberMe, {expires: date});
                        } else {
                            $.removeCookie('userId');
                            $.removeCookie('rememberMe');
                        }

                        if (userInfo.user.role == 'Student') {
                            $location.path('/student');
                        } else {
                            $location.path('/admin');
                        }
                    }
                }, function (result, status, headers, config) {

        });
    };
    $rootScope.logout = function () {
        authAPIService.logout().then(function (result) {
            $rootScope.loggedInUser = null;
            $location.path('/login');
        });
    };
    $scope.isPRSubmitted = false;
    $scope.resetPassword = function () {
        $scope.isPRSubmitted = false;
        dialogAPIService.createDialog(DIALOGS.FORGOTPWD.TEMPLATE, {
            id: 'resetDialog',
            title: DIALOGS.FORGOTPWD.TITLE,
            top: 30,
            height: 600,
            width: 600,
            type: 'sdialog',
            text: '',
            backdrop: true,
            withFooter: false,
            controller: 'LoginController'
        });
    };
    $scope.pwdresetdetails = {email: '', regNo: ''};
    $scope.pwdinfo = null;
    $scope.submit4ResetPwd = function () {
        if (!$scope.isPRSubmitted) {
            var pwdForm = $scope.pwdresetdetails;
            swaAPIService.request2ResetPwd(pwdForm).then(
                    function (result, status, headers, config) {
                        if (result.status == 200) {
                            $scope.isPRSubmitted = true;
                            $scope.pwdinfo = MESSAGES.FORGOT_PWD_SUCCESS;
                        } else {
                            $scope.pwdinfo = MESSAGES.FORGOT_PWD_FAILED;
                        }
                    },
                    function (result, status, headers, config) {
                        $scope.isPRSubmitted = false;
                        $scope.pwdinfo = MESSAGES.FORGOT_PWD_ERROR;
                    });
        } else {
            $scope.pwdinfo = MESSAGES.FORGOT_PWD_ALREADY_SENT;
        }
    };
    $scope.cancel2Submit = function () {
        $scope.isPRSubmitted = false;
    };
}).controller('StoryController', function ($scope, $rootScope, $timeout, swaAPIService, dialogAPIService) {
    $scope.maxStorySize = MAXSTORYCHARS;
    $scope.story = {
        topicId: '',
        submit: '',
        storyTitle: '',
        storyText: ''
    };
    $scope.topics = [];
    function loadUserStory() {
        swaAPIService.getUserStory().then(
                function (result, status, headers, config) {
                    try {
                        if (result.data != 'Story Not Found') {
                            $scope.story = result.data;
                            $scope.isSubmitted = $scope.story.submit == 'Submit';
                        } else {
                            //$location.path('/view');
                        }
                    } catch (e) {
                    }
                },
                function (result, status, headers, config) {
                    //alert(result);
                }
        );
    }

    function loadTopics() {
        swaAPIService.allTopics().then(
                function (result, status, headers, config) {
                    $.each(result.data, function (i, o) {
                        $scope.topics.push({id: o.id, name: o.name});
                    });
                    loadUserStory();
                },
                function (result, status, headers, config) {
                }
        );
    }
    loadTopics();
    $scope.validateStory = function () {
        var l = ($scope.story.storyText == null && $scope.story.storyText == '') ? 0 : $scope.story.storyText.length;
        if (l > MAXSTORYCHARS) {
            $scope.story.storyText = '';
            $scope.message = MESSAGES.STORY_MAX_CHARS;
            $scope.maxStorySize = MAXSTORYCHARS;
        } else {
            $scope.maxStorySize = MAXSTORYCHARS - l;
        }
    };
    $scope.clickStory = function () {
        $scope.message = '';
    };
    $scope.message = null;
    $scope.storyFile = null;
    $scope.showContent = function ($fileContent, $message) {
        var l = ($fileContent == null || $fileContent == '') ? 0 : $fileContent.length;
        if (l > MAXSTORYCHARS) {
            $scope.story.storyText = '';
            $scope.message = MESSAGES.STORY_UPLOAD_MAX_CHARS;
        } else {
            $scope.story.storyText = $fileContent;
            $scope.maxStorySize = MAXSTORYCHARS - l;
            $scope.message = $message;
        }
    };
    $scope.setAction = function (action) {
        $scope.story.submit = action;
    };
    $scope.isSubmitted = false;
    $scope.submitStory = function () {
        var story = $scope.story;
        if (story.submit == 'Submit') {
            var content = MESSAGES.STORY_SUBMISSION_CONFIRMATION;
            dialogAPIService.createDialog(DIALOGS.STORY.TEMPLATE, {
                id: 'confDialog',
                title: DIALOGS.STORY.TITLE,
                top: 150,
                height: 350,
                width: 400,
                type: 'confirm',
                text: content,
                backdrop: true,
                success: {label: 'Confirm', hide: true, fn: function () {
                        confirm2Submit(story);
                    }}
            });
        } else {
            confirm2Submit(story);
        }
    };
    $scope.alerts = [];
    function confirm2Submit(story) {
        swaAPIService.submitStoryForm(story).then(
                function (result, status, headers, config) {
                    if (result.data == 'Not Added') {
                        $scope.isSubmitted = false;
                        $scope.alerts = swaAPIService.addAlert('Story is not ' + ($scope.isSubmitted ? 'submitted' : 'saved'), 'alert-danger alert-center');
                    } else {

                        $scope.isSubmitted = (story.submit == 'Submit');
                        $scope.alerts = swaAPIService.addAlert('Story ' + ($scope.isSubmitted ? 'submitted' : 'saved') + ' successfully!', 'alert-success alert-center');
                    }
                }, function (result, status, headers, config) {
            $scope.isSubmitted = false;
        });
    }
}).controller('StudentController', function ($scope, $rootScope, $compile, $window, $location, $sce, swaAPIService, authAPIService) {
    /*Student Controller */
    var activities = [
        {item: '0 start', styleClass: 'bgData0', header: 'START YOUR STORY', subheader: '', description: 'Story submission date is from <start date> to <end date>', summary: 'Start Writing Now', page: {button: 'Start Your Story', link: '#/story'}, menu: {button: 'Submit Your Story', link: '#/story'}},
        {item: '1 edit', styleClass: 'bgData1', header: 'YOUR STORY', subheader: 'You are yet to submit your story', description: 'Story found saved', summary: '', page: {button: 'Continue Writing', link: '#/story'}, menu: {button: 'Submit Your Story', link: '#/story'}},
        {item: '1 view', styleClass: 'bgData1', header: 'YOUR STORY', subheader: 'Story rating date is from <from> to <to>', description: 'Story Submitted but Review not stared', summary: '', page: {button: 'View Story', link: '#/view'}, menu: {button: 'Review Story', link: '#/view'}},
        {item: '2 review', styleClass: 'bgData2', header: 'REVIEW STORIES', subheader: 'Review the assigned stories', description: 'Review Started', summary: '', page: {button: 'Review Stories', link: '#/review'}, menu: {button: 'Review Stories', link: '#/review'}},
        {item: '3 winner', styleClass: 'bgData3', header: 'RESULTS ON <date>', subheader: '', description: 'Please visit this section on <Announce date>  to know the results.', summary: '', page: null, menu: {button: 'View Your Story', link: '#/view'}}
    ];
    var mystory = '';
    function loadUserStory() {
        swaAPIService.getUserStory().then(
                function (result, status, headers, config) {
                    try {
                        var story = result.data;
                        if (story != 'Story Not Found') {
                            mystory += '<div id="titleDiv" class="textdeco">' + story.storyTitle + '</div>';
                            mystory += story.storyText.substring(0, 400) + ' ...';
                        } else {
                        }
                    } catch (e) {
                    }
                },
                function (result, status, headers, config) {
                }
        );
    }

    loadUserStory();
    var ostory = '';
    function loadOtherStories() {
        swaAPIService.getOtherStories().then(
                function (result, status, headers, config) {
                    $.each(result.data, function (i, o) {
                        ostory += '<div id="titleDivO" class="revLink" index="' + i + '">' + o.title + (o.totalMarks > 0 ? '&nbsp;<img src="images/reviewed.png" border="0">' : '') + '</div>';
                        ostory += '<span>' + o.storyText.substring(0, 50) + ' ...</span>';
                    });
                },
                function (result, status, headers, config) {
                }
        );
    }

    loadOtherStories();
    $rootScope.activity = null;
    function loadActivities() {
        swaAPIService.getActivities().then(
                function (result, status, headers, config) {
                    var userInfo = result.data;
                    var idx = 0;
                    var description = null;
                    var subheader = null;
                    if (userInfo.isStorySubmitted == false && userInfo.isSaved == false) {
                        idx = 0;
                        if (userInfo.isReviewStarted == true || userInfo.isAnnouncementStarted == true) {
                            idx = 4;
                        } else {
                            description = '<span class="text-center fontStyle">Story submission date is from ' + niceDate(userInfo.startDate) + ' to ' + niceDate(userInfo.endDate) + '.</span>';
                        }
                    } else if (userInfo.isStorySubmitted == false && userInfo.isSaved == true) {
                        idx = 1;
                        if (userInfo.isReviewStarted == true || userInfo.isAnnouncementStarted == true) {
                            idx = 4;
                        } else {
                            description = mystory;
                        }
                    } else if (userInfo.isStorySubmitted == true && userInfo.isReviewStarted == false) {
                        idx = 2;
                        description = mystory;
                        subheader = '<span class="text-center fontStyle textNorm">Story rating date is from ' + niceDate(userInfo.startDate) + ' to ' + niceDate(userInfo.endDate) + '.</span>';
                    } else if (userInfo.isReviewStarted == true && userInfo.isReviewed == false) {
                        idx = 3;
                        if (!userInfo.isReviewClosed && userInfo.isStorySubmitted == true) {
                            description = ostory;
                        } else {
                            idx = 4;
                        }
                    } else if (userInfo.isReviewStarted == true && userInfo.isReviewed == true) {
                        idx = 4;
                    }
                    if (idx == 4) {
                        activities[idx].header = 'RESULTS ON ' + niceDate(userInfo.startDate);
                        description = '<span class="text-left fontStyle marginTrophy"><img src="images/reviewed.png" border="0" class="imgpos">Please visit this section on ' + niceDate(userInfo.startDate) + ' to know the results.</span>';
                    }
                    if (description != null)
                        activities[idx].description = $sce.trustAsHtml(description);
                    if (subheader != null)
                        activities[idx].subheader = subheader;
                    $rootScope.activity = activities[idx];
                    userInfo.activity = $rootScope.activity;
                    authAPIService.setUserInfo(userInfo);
                },
                function (result, status, headers, config) {
                }
        );
    }
    loadActivities();
    var storyIndex = 0;
    $scope.reviewStory = function (index) {
        storyIndex = index;
        $location.path('/review');
    };
    $rootScope.getStoryIndex = function () {
        return storyIndex;
    };
}).controller('ViewController', function ($scope, $rootScope, $timeout, dialogAPIService, swaAPIService, authAPIService) {
    $scope.stories = [
        {index: 0, isLoaded: false, story: []},
        {index: 1, isLoaded: false, story: [{title: null, text: null, isReviewed: true}]}
    ];
    function loadUserStory() {
        swaAPIService.getUserStory().then(
                function (result, status, headers, config) {
                    var story = result.data;
                    if (story != 'Story Not Found') {
                        $scope.stories[1].isLoaded = true;
                        $scope.stories[1].story[0].title = story.storyTitle;
                        $scope.stories[1].story[0].text = story.storyText;
                        $scope.isReviewed = $scope.stories[1].story[0].isReviewed;
                        $scope.storyText = $scope.stories[1].story[0].text;
                    }
                },
                function (result, status, headers, config) {
                }
        );
    }
    $scope.tabNo = 3;
    function loadOtherStories(idx) {
        if ($scope.stories[0].isLoaded)
            return;
        swaAPIService.getOtherStories().then(
                function (result, status, headers, config) {
                    if (result.data.length > 0) {
                        $scope.tabNo = 12 / result.data.length;
                        $.each(result.data, function (i, o) {
                            var tooltip = o.title;
                            var title = tooltip.length > 28 ? tooltip.substring(0, 24) + ' ...' : tooltip;
                            var ostory = {index: i, id: o.id, title: title, tooltip: tooltip, arrow: '', active: '', text: o.storyText, isReviewed: o.totalMarks > 0};
                            if (idx == i) {
                                ostory.arrow = 'arrow-up arrow-position';
                                ostory.active = 'active';
                            }
                            $scope.stories[0].story.push(ostory);
                        });
                        $scope.stories[0].isLoaded = true;
                        markStory(idx);
                    }
                },
                function (result, status, headers, config) {
                }
        );
    }
    $scope.isReviewed = true;
    $scope.isReview = true;
    $scope.storyText = null;
    $scope.storyId = null;
    $scope.viewStory = function (index) {
        $('#tab' + (1 - index)).removeClass("active in");
        $('#tab' + index).addClass("active in");
        if (index == 0) {
            tabIndex = 0;
            storyIndex = 0;
            $scope.isReview = true;
            $scope.viewOtherStory(0);
        } else {
            $scope.isReview = false;
            $scope.isReviewed = true;
            tabIndex = 1;
            storyIndex = 0;
            if (!$scope.stories[1].isLoaded) {
                loadUserStory();
            } else {
                $scope.storyText = $scope.stories[1].story[0].text;
            }
        }
    };
    $scope.viewOtherStory = function (index) {
        if (!$scope.stories[0].isLoaded)
            loadOtherStories(index);
        else
            markStory(index);
    };
    $scope.isReviewed = false;
    var tabIndex = 0;
    var storyIndex = 0;
    function markStory(index) {
        tabIndex = 0;
        storyIndex = index;
        $scope.isReview = true;
        $scope.storyId = $scope.stories[0].story[index].id;
        $scope.storyText = $scope.stories[0].story[index].text;
        $scope.isReviewed = $scope.stories[0].story[index].isReviewed;
        $(".arrow-up").removeClass("arrow-up");
        var tab = $('.col-md-' + $scope.tabNo + ':eq(' + index + ')');
        $('li[name=navtab]').removeClass("active");
        $(tab).addClass('active');
        $(tab).find("span").addClass("arrow-up arrow-position");
    }

    var tIdx = 0;
    var sIdx = 0;
    try {
        if (tIdx == undefined)
            tIdx = 0;
        if (sIdx == undefined)
            sIdx = 0;
        sIdx = $rootScope.getStoryIndex();
    } catch (e) {
    }
    loadOtherStories(sIdx);
    $rootScope.setReviewStatus = function (flag) {
        $scope.isReviewed = flag;
        $scope.stories[0].story[storyIndex].isReviewed = flag;
    };
    /*review a story*/
    $scope.giveReviewMarks = function (storyId) {
        dialogAPIService.createDialog(DIALOGS.REVIEW.TEMPLATE, {
            id: 'reviewDialog',
            title: DIALOGS.REVIEW.TITLE,
            top: 30,
            height: 563,
            width: 800,
            type: 'dialog',
            text: '',
            backdrop: true,
            withFooter: false,
            controller: 'ReviewController'
        }, {storyId: storyId});
    };
    $rootScope.getStoryId = function () {
        return $scope.storyId;
    };
}).controller('ReviewController', function ($scope, $rootScope, $timeout, swaAPIService) {
    $scope.questions = [
        {index: 0, id: 1, text: QUESTIONS.ONE, styleClass: 'mood', mark: 0, unit: 1, range: {lower: 0, upper: 9, step: 1}, margin: 30},
        {index: 1, id: 2, text: QUESTIONS.TWO, styleClass: 'tick', mark: 0, unit: 1, range: {lower: 0, upper: 9, step: 1}, margin: 30},
        {index: 2, id: 3, text: QUESTIONS.THREE, styleClass: 'thumb', mark: 0, unit: 1, range: {lower: 0, upper: 9, step: 1}, margin: 30},
        {index: 3, id: 4, text: QUESTIONS.FOUR, styleClass: 'face', mark: 0, unit: 1, range: {lower: 0, upper: 9, step: 1}, margin: 0}
    ];
    function loadReviewQuestions() {
        swaAPIService.getReviewQuestions().then(
                function (result, status, headers, config) {
                    //alert('question='+JSON.stringify(result.data));
                    $.each(result.data, function (i, o) {
                        //alert(i + ' Q '+JSON.stringify(o));
                        $scope.questions[i].id = o.id;
                        $scope.questions[i].text = o.description;
                        $scope.questions[i].unit = o.unit;
                        $scope.questions[i].mark = 0;
                    });
                },
                function (result, status, headers, config) {
                    //alert(result);
                }
        );
    }
    loadReviewQuestions();
    $scope.storyId = $rootScope.getStoryId();
    $scope.setMark = function (index, mark) {
        var selQues = $scope.questions[index].styleClass;
        $.each($('.sel' + selQues), function (idx, item) {
            $(this).attr('class', selQues + ' iconmargin');
        });
        var qObj = $('.' + selQues);
        for (var i = 0; i <= mark; i++) {
            $(qObj[i]).attr('class', 'sel' + selQues + ' iconmargin');
        }
        ;
        $scope.questions[index].mark = ($scope.questions[index].unit) * (mark + 1);
    };
    function setDefaultMarks() {
        $.each($scope.questions, function (i, o) {
            $scope.questions[i].mark = o.unit;
            var selQues = o.styleClass;
            var qObj = $('.' + selQues);
            $(qObj[0]).attr('class', 'sel' + selQues + ' iconmargin');
        });
    }

    $scope.onTimeout = function () {
        setDefaultMarks();
    };
    var mytimeout = $timeout($scope.onTimeout, 100);
    $scope.$on("$destroy", function () {
        $timeout.cancel(mytimeout);
    });
    $scope.isSubmitted = false;
    $scope.revmsg = null;
    $scope.confirm2Submit = function (storyId) {
        var reviewForm = {
            storyId: storyId,
            questions: []
        };
        var marks = $scope.questions;
        for (var i = 0; i < marks.length; i++) {
            //alert(i + ' $$$ '+marks[i].text +'=>' + $rootScope.marks[i].mark);
            reviewForm.questions.push({id: marks[i].id, weightage: marks[i].mark});
        }
        swaAPIService.submitReviews(reviewForm).then(
                function (result, status, headers, config) {
                    $scope.isSubmitted = true;
                    if (result.status == 200) {
                        $rootScope.setReviewStatus(true);
                        $('#msgDiv').html('<p class="revconf">' + MESSAGES.REVIEW_STROY_SUCCESS + '</p>');
                    }
                }, function (result, status, headers, config) {
            $rootScope.setReviewStatus(false);
            $scope.isSubmitted = false;
        });
    };
}).controller('StaticController', function ($scope, dialogAPIService) {
    $scope.staticLinks = staticLinks;
    $scope.showStaticContent = function (code) {
        var link = $scope.staticLinks[code];
        dialogAPIService.createDialog(link.url, {
            id: 'frameDialog',
            title: link.title,
            top: 20,
            height: 1000,
            width: 1200,
            type: 'frame',
            text: link.title,
            backdrop: true,
            withFooter: false
        }, {code: code});
    };
}).controller('HeaderController', function ($scope, $rootScope, swaAPIService) {
    $scope.headerLinks = headerLinks;
}).controller('FooterController', function ($scope, $rootScope, swaAPIService) {
    $scope.footerLinks = footerLinks;
}).controller('WinnerController', function ($scope, $rootScope, swaAPIService) {
    $scope.winners = winners;
}).controller('AdminController', function ($scope, $rootScope, swaAPIService, $location, dialogAPIService, authAPIService, $timeout) {

    /*Admin controller*/
    $scope.tasks = [];
    var activity = {menu: {button: 'Submit Your Story', link: '#/admin'}};
    function loadTasks() {
        var userInfo = authAPIService.getUserInfo();
        if (userInfo != null && userInfo != 'null') {
            userInfo.activity = activity;
            authAPIService.setUserInfo(userInfo);
            swaAPIService.getTasks().then(
                    function (result, status, headers, config) {
                        $.each(result.data, function (i, o) {
                            var tooltip = o.description;
                            var description = tooltip.length > 30 ? tooltip.substring(0, 30) + ' ...' : tooltip;
                            $scope.tasks.push({id: o.id, description: description, tooltip: tooltip, startDate: o.startDate, endDate: o.endDate, duration: niceDuration(o.startDate, o.endDate), closeBtn: o.id > 3});
                        });
                    },
                    function (result, status, headers, config) {
                    }
            );
        } else {
            $location.path('/login');
        }
    }
    loadTasks();
    $scope.deleteTask = function (index) {
        if (confirm(MESSAGES.TASK_DELETE_CONFIRM)) {
            swaAPIService.deleteTask($scope.tasks[index].id).then(
                    function (result, status, headers, config) {
                        if (result.status == 200) {
                            $scope.tasks.splice(index, 1);
                        } else {
                            alert(MESSAGES.TASK_NOT_DELETED);
                        }
                    },
                    function (result, status, headers, config) {
                    });
        }
    };
    var taskIndex = -1;
    $rootScope.getTask = function () {
        return taskIndex > -1 ? $scope.tasks[taskIndex] : null;
    };
    $rootScope.setTask = function (result) {
        var tooltip = result.description;
        var description = tooltip.length > 30 ? tooltip.substring(0, 30) + ' ...' : tooltip;
        if (taskIndex > -1) {
            $scope.tasks[taskIndex] = {id: result.id, description: description, tooltip: tooltip, startDate: result.startDate, endDate: result.endDate, duration: niceDuration(result.startDate, result.endDate), closeBtn: false};
        } else {
            $scope.tasks.push({id: result.id, description: description, tooltip: tooltip, startDate: result.startDate, endDate: result.endDate, duration: niceDuration(result.startDate, result.endDate), closeBtn: true});
        }
        taskIndex = -1;
    };
    $scope.openTaskDialog = function (index) {
        taskIndex = index;
        dialogAPIService.createDialog(DIALOGS.TASK.TEMPLATE, {
            id: 'taskDialog',
            title: taskIndex > -1 ? DIALOGS.TASK.TITLE.EDIT : DIALOGS.TASK.TITLE.ADD,
            top: 100,
            height: 600,
            width: 600,
            type: 'sdialog',
            text: '',
            backdrop: true,
            withFooter: false,
            controller: 'TaskController'
        });
    };
//End Add new Task

    $scope.groups = [];
    $scope.group = {id: 0, text: ''};
    $scope.setGroup = function (index) {
        var g = $scope.groups[index];
        $scope.topic.groupId = g.id;
        $scope.topic.groupName = g.name;
        //$scope.topic.group = g.text;
        $scope.group = {id: g.id, name: g.name};
    };
    $scope.topics = [];
    function loadGroups() {
        swaAPIService.getGroups().then(
                function (result, status, headers, config) {
                    // alert('group & tpoic='+JSON.stringify(result.data));
                    var groups = result.data.group;
                    $.each(groups, function (i, o) {
                        $scope.groups.push({id: o.id, name: o.name});
                        //alert('topic='+JSON.stringify(o.topics));
                        $.each(o.topics, function (j, t) {
                            if (t.deleted == 0) {
                                var tooltip = t.name;
                                var topic = tooltip.length > 30 ? tooltip.substring(0, 30) + ' ...' : tooltip;
                                $scope.topics.push({id: t.id, name: topic, tooltip: tooltip, group: o.name});
                            }
                        });
                    });
                },
                function (result, status, headers, config) {
                }
        );
    }
    loadGroups();
    $scope.topic = {topicId: '', topicName: '', groupId: '', groupName: ''};
    $scope.addTopic = function () {
        var isOk = true;
        $scope.topicmsg = '';
        $scope.groupmsg = '';
        if ($scope.topic.topicName == '') {
            isOk = false;
            $scope.topicmsg = MESSAGES.TOPIC_INFO;
        }
        if ($scope.topic.groupId == '') {
            $scope.groupmsg = MESSAGES.TOPIC_GROUP_INFO;
            isOk = false;
        }
        if (isOk) {
            var topic = $scope.topic;
            swaAPIService.submitTopic(topic).then(
                    function (result, status, headers, config) {
                        var resTopic = result.data;
                        if (resTopic == 'Not Added') {
                            alert('Topic \'' + topic.topicName + '\' already existing in group \'' + topic.groupName + '\'');
                        } else if (resTopic.topicId != null) {
                            $scope.topic.topicName = '';
                            $scope.topic.groupId = '';
                            $scope.topic.groupName = '';
                            $('#grp').html('Select Group');
                            var tooltip = resTopic.topicName;
                            var topic1 = tooltip.length > 30 ? tooltip.substring(0, 30) + ' ...' : tooltip;
                            $scope.topics.push({id: resTopic.topicId, name: topic1, tooltip: tooltip, group: resTopic.groupName});
                        }
                    },
                    function (result, status, headers, config) {
                    }
            );
        }
    };
    var isFirstAttempt = true;
    $scope.deleteTopic = function (index) {
        swaAPIService.deleteTopic($scope.topics[index].id, isFirstAttempt).then(
                function (result, status, headers, config) {
                    if (result.status == 200) {
                        if (isFirstAttempt) {
                            if (confirm(MESSAGES.TOPIC_DELETE_CONFIRM)) {
                                isFirstAttempt = false;
                                $scope.deleteTopic(index);
                            }
                        } else {
                            isFirstAttempt = true;
                            $scope.topics.splice(index, 1);
                        }
                    } else {
                        isFirstAttempt = true;
                        alert(MESSAGES.TOPIC_DELETE_WORNING);
                    }
                },
                function (result, status, headers, config) {
                }
        );
    };
    $scope.topStories = null;
    function loadTopStories() {
        swaAPIService.getTopStories().then(
                function (result, status, headers, config) {
                    $scope.topStories = result.data;
                },
                function (result, status, headers, config) {
                }
        );
    }

    loadTopStories();
    $scope.topParticipants = null;
    function loadTopParticipants() {
        swaAPIService.getTopParticipants().then(
                function (result, status, headers, config) {
                    $scope.topParticipants = result.data;
                },
                function (result, status, headers, config) {
                }
        );
    }

    loadTopParticipants();
    $scope.groupId = null;
    $scope.storycountmsg = null;
    $scope.storygroupmsg = null;
    $scope.participantsgroupId = null;
    $scope.participantscountmsg = null;
    $scope.participantsgroupmsg = null;
    var groupName = '';
    $scope.setStoryExportGroup = function (index) {
        var g = $scope.groups[index];
        $scope.groupId = g.id;
        groupName = g.name;
        $scope.storygroupmsg = '';
    };
    var participantsgroupName = '';
    $scope.setParticipantsExportGroup = function (index) {
        var g = $scope.groups[index];
        $scope.participantsgroupId = g.id;
        participantsgroupName = g.name;
        $scope.participantsgroupmsg = '';
    };
    $scope.noOfParticipants = 0;
    $scope.setNoOfParticipants = function (count) {
        $scope.noOfParticipants = count;
        $scope.participantscountmsg = '';
    };
    $scope.participantsexportData = function () {
        var isOk = true;
        $scope.participantscountmsg = '';
        $scope.participantsgroupmsg = '';
        var participantsgroupId = $scope.participantsgroupId;
        if (participantsgroupId == null || participantsgroupId == '') {
            isOk = false;
            $scope.participantsgroupmsg = MESSAGES.EXPORT_GROUP_INFO;
        }
        var participantsCount = $scope.noOfParticipants;
        if (participantsCount == 0) {
            $scope.participantscountmsg = MESSAGES.EXPORT_TOP_PARTICIPANTS_INFO;
            isOk = false;
        }

        if (isOk) {
            $scope.participantsgroupmsg = '';
            $scope.participantscountmsg = 'Top ' + participantsCount + ' participants export processing ...';
            swaAPIService.exportParticipantData(participantsCount, participantsgroupId).then(
                    function (result, status, headers, config) {
                        if (result.status == 200) {
                            if (result.data != 'Data Not Found') {
                                var blob = new Blob([result.data], {type: 'text/csv'});
                                var fileName = 'top ' + participantsCount + ' participants for ' + participantsgroupName + ' ' + new Date() + '.csv';
                                saveAs(blob, fileName);
                                $scope.participantscountmsg = 'Top ' + participantsCount + ' participants export processed ...';
                                $timeout(function () {
                                    $scope.participantscountmsg = "";
                                }, 5000);
                            }
                            else {
                                $scope.participantscountmsg = MESSAGES.TOP_STORIES_EXPORT_NO_DATA;
                            }
                        } else {
                            $scope.participantscountmsg = MESSAGES.TOP_STORIES_EXPORT_NO_DATA;
                        }
                    },
                    function (result, status, headers, config) {
                        $scope.participantscountmsg = 'Top ' + participantsCount + ' participants export request not processed ...';
                    }
            );
        }
    };
    $scope.noOfStory = 0;
    $scope.setNoOfStory = function (count) {
        $scope.noOfStory = count;
        $scope.storycountmsg = '';
    };
    $scope.exportAsPdf = function () {
        var isOk = true;
        $scope.storycountmsg = '';
        $scope.storygroupmsg = '';
        var groupId = $scope.groupId;
        if (groupId == null || groupId == '') {
            isOk = false;
            $scope.storygroupmsg = MESSAGES.EXPORT_GROUP_INFO;
        }
        var storyCount = $scope.noOfStory;
        if (storyCount == 0) {
            $scope.storycountmsg = MESSAGES.EXPORT_TOP_STORIES_INFO;
            isOk = false;
        }

        if (isOk) {
            $scope.storygroupmsg = '';
            $scope.storycountmsg = 'Top ' + storyCount + ' stories export processing ...';
            swaAPIService.exportAsPdf(storyCount, groupId).then(
                    function (result, status, headers, config) {
                        if (result.status == 200) {
                            if (result.data != 'Data Not Found') {
                                var blob = new Blob([result.data], {type: 'application/octet-stream'});
                                var fileName = 'top ' + storyCount + ' stories for ' + groupName + ' ' + new Date() + '.zip';
                                saveAs(blob, fileName);
                                $scope.storycountmsg = 'Top ' + storyCount + ' stories export processed ...';
                                $timeout(function () {
                                    $scope.storycountmsg = "";
                                }, 5000);
                            } else {
                                $scope.storycountmsg = MESSAGES.TOP_STORIES_EXPORT_NO_DATA;
                            }
                        } else {
                            $scope.storycountmsg = MESSAGES.TOP_STORIES_EXPORT_NO_DATA;
                        }
                    },
                    function (result, status, headers, config) {
                        $scope.storycountmsg = 'Top ' + storyCount + ' stories export request not processed ...';
                    }
            );
        }
    };
    $scope.studentexpmsg = null;
    $scope.exportStdents = function () {
        $scope.studentexpmsg = MESSAGES.STUDENT_EXPORT_PROCESSING;
        swaAPIService.exportStdents().then(
                function (result, status, headers, config) {
                    if (result.status == 200) {
                        if (result.data != 'Data Not Found') {
                            var blob = new Blob([result.data], {type: 'text/csv'});
                            saveAs(blob, 'all students ' + new Date() + '.csv');
                            $scope.studentexpmsg = MESSAGES.STUDENT_EXPORT_PROCESSED;
                            $timeout(function () {
                                $scope.studentexpmsg = "";
                            }, 5000);
                        } else {
                            $scope.studentexpmsg = MESSAGES.STUDENT_EXPORT_NOT_FOUND;
                        }
                    } else {
                        $scope.studentexpmsg = MESSAGES.STUDENT_EXPORT_NOT_FOUND;
                    }
                },
                function (result, status, headers, config) {
                    $scope.studentexpmsg = MESSAGES.STUDENT_EXPORT_NOT_PROCESSED;
                }
        );

    };

    $scope.allschooldataexpmsg = null;
    $scope.exportAllSchoolData = function () {
        $scope.allschooldataexpmsg = MESSAGES.ALL_SCHOOL_DATA_EXPORT_PROCESSING;
        swaAPIService.exportAllSchoolData().then(
                function (result) {
                    if (result.status === 200) {
                        if (result.data !== 'Data Not Found') {
                            var blob = new Blob([result.data], {type: 'text/csv'});
                            saveAs(blob, 'all schools data ' + new Date() + '.csv');
                            $scope.allschooldataexpmsg = MESSAGES.ALL_SCHOOL_DATA_EXPORT_PROCESSED;
                            $timeout(function () {
                                $scope.allschooldataexpmsg = "";
                            }, 5000);
                        } else {
                            $scope.allschooldataexpmsg = MESSAGES.ALL_SCHOOL_DATA_EXPORT_NOT_FOUND;
                        }
                    } else {
                        $scope.allschooldataexpmsg = MESSAGES.ALL_SCHOOL_DATA_EXPORT_NOT_FOUND;
                    }
                },
                function () {
                    $scope.allschooldataexpmsg = MESSAGES.ALL_SCHOOL_DATA_EXPORT_NOT_PROCESSED;
                }
        );
    };


    $scope.schoolparticipatedexpmsg = null;
    $scope.exportSchoolParticipated = function () {
        $scope.schoolparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_PARTICIPATED_EXPORT_PROCESSING;
        swaAPIService.exportSchoolParticipated(true).then(
                function (result, status, headers, config) {
                    if (result.status == 200) {
                        if (result.data != 'Data Not Found') {
                            var blob = new Blob([result.data], {type: 'text/csv'});
                            saveAs(blob, 'all network schools participated ' + new Date() + '.csv');
                            $scope.schoolparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_PARTICIPATED_EXPORT_PROCESSED;
                            $timeout(function () {
                                $scope.schoolparticipatedexpmsg = "";
                            }, 5000);
                        } else {
                            $scope.schoolparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_PARTICIPATED_EXPORT_NOT_FOUND;
                        }
                    } else {
                        $scope.schoolparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_PARTICIPATED_EXPORT_NOT_FOUND;
                    }
                },
                function (result, status, headers, config) {
                    $scope.schoolparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_PARTICIPATED_EXPORT_NOT_PROCESSED;
                }
        );
    };
    $scope.schoolnotparticipatedexpmsg = null;
    $scope.exportSchoolNotParticipated = function () {
        $scope.schoolnotparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_NOT_PARTICIPATED_EXPORT_PROCESSING;
        swaAPIService.exportSchoolParticipated(false).then(
                function (result, status, headers, config) {
                    if (result.status == 200) {
                        if (result.data != 'Data Not Found') {
                            var blob = new Blob([result.data], {type: 'text/csv'});
                            saveAs(blob, 'all network schools not participated ' + new Date() + '.csv');
                            $scope.schoolnotparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_NOT_PARTICIPATED_EXPORT_PROCESSED;
                            $timeout(function () {
                                $scope.schoolnotparticipatedexpmsg = "";
                            }, 5000);
                        } else {
                            $scope.schoolnotparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_NOT_PARTICIPATED_EXPORT_NOT_FOUND;
                        }
                    } else {
                        $scope.schoolnotparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_NOT_PARTICIPATED_EXPORT_NOT_FOUND;
                    }
                },
                function (result, status, headers, config) {
                    $scope.schoolnotparticipatedexpmsg = MESSAGES.NETWORK_SCHOOL_NOT_PARTICIPATED_EXPORT_NOT_PROCESSED;
                }
        );
    };
    $scope.nonnetworkschoolexpmsg = null;
    $scope.exportnonNetworkSchool = function () {
        $scope.nonnetworkschoolexpmsg = MESSAGES.OUT_OF_NETWORK_SCHOOL_EXPORT_PROCESSING;
        swaAPIService.exportNonNetworkSchool().then(
                function (result, status, headers, config) {
                    if (result.status == 200) {
                        if (result.data != 'Data Not Found') {
                            var blob = new Blob([result.data], {type: 'text/csv'});
                            saveAs(blob, 'out of network school ' + new Date() + '.csv');
                            $scope.nonnetworkschoolexpmsg = MESSAGES.OUT_OF_NETWORK_SCHOOL_EXPORT_PROCESSED;
                            $timeout(function () {
                                $scope.nonnetworkschoolexpmsg = "";
                            }, 5000);
                        } else {
                            $scope.nonnetworkschoolexpmsg = MESSAGES.OUT_OF_NETWORK_SCHOOL_EXPORT_NOT_FOUND;
                        }
                    } else {
                        $scope.nonnetworkschoolexpmsg = MESSAGES.OUT_OF_NETWORK_SCHOOL_EXPORT_NOT_FOUND;
                    }
                },
                function (result, status, headers, config) {
                    $scope.nonnetworkschoolexpmsg = MESSAGES.OUT_OF_NETWORK_SCHOOL_EXPORT_NOT_PROCESSED;
                }
        );
    };
    $scope.submissiondetailsexpmsg = null;
    $scope.exportSubmissionDetails = function () {
        $scope.submissiondetailsexpmsg = MESSAGES.COMPLETE_SUBMISSION_DETAILS_EXPORT_PROCESSING;
        swaAPIService.exportSubmissionDetails().then(
                function (result) {
                    if (result.status === 200) {
                        if (result.data !== 'Data Not Found') {
                            var blob = new Blob([result.data], {type: 'text/csv'});
                            saveAs(blob, 'complete submission details ' + new Date() + '.csv');
                            $scope.submissiondetailsexpmsg = MESSAGES.COMPLETE_SUBMISSION_DETAILS_EXPORT_PROCESSED;
                            $timeout(function () {
                                $scope.submissiondetailsexpmsg = "";
                            }, 5000);
                        } else {
                            $scope.submissiondetailsexpmsg = MESSAGES.COMPLETE_SUBMISSION_DETAILS_EXPORT_NOT_FOUND;
                        }
                    } else {
                        $scope.submissiondetailsexpmsg = MESSAGES.COMPLETE_SUBMISSION_DETAILS_EXPORT_NOT_FOUND;
                    }
                },
                function () {
                    $scope.submissiondetailsexpmsg = MESSAGES.COMPLETE_SUBMISSION_DETAILS_EXPORT_NOT_PROCESSED;
                }
        );
    };
    $scope.uploadBanner = function () {
        dialogAPIService.createDialog(DIALOGS.UPLOAD.TEMPLATE, {
            id: 'uploadDialog',
            title: DIALOGS.UPLOAD.TITLE,
            top: 30,
            height: 700,
            width: 800,
            type: 'dialog',
            text: '',
            backdrop: true,
            withFooter: false,
            success: {label: 'Submit', hide: true, fn: function () {
                }},
            close: {label: 'Close', fn: function () {
                }},
            controller: 'UploadController'
        }, {});
    };
    $rootScope.getDateBounds = function () {
        if (taskIndex == -1) {
            var minDate = $scope.tasks[$scope.tasks.length - 1].endDate;
            return {index: $scope.tasks.length,
                start: {minDate: boundDate(minDate, 1), maxDate: null},
                end: {minDate: boundDate(minDate, 1), maxDate: null}
            };
        }
        var minDate = new Date();
        minDate.setHours(0, 0, 0, 0);
        var taskLength = $scope.tasks.length;
        var minSDate = taskIndex == 0 ? minDate : angular.copy($scope.tasks[taskIndex - 1].endDate);
        var minEDate = taskIndex !== taskLength - 1 ? angular.copy($scope.tasks[taskIndex + 1].startDate) : null;
        if (taskIndex != 0) {
            if (typeof minSDate === 'string' || minSDate instanceof String) {
                minSDate = boundDate(minSDate, 0);
                minSDate = new Date(minSDate);
                minSDate.setDate(minSDate.getDate() + 1);
                minSDate.setHours(0, 0, 0, 0);
            } else {
                minSDate.setDate(minSDate.getDate() + 1);
            }
        }
        if (taskIndex !== taskLength - 1) {
            if (typeof minEDate === 'string' || minEDate instanceof String) {
                minEDate = boundDate(minEDate, 0);
                minEDate = new Date(minEDate);
                minEDate.setDate(minEDate.getDate() - 1);
                minEDate.setHours(0, 0, 0, 0);
            } else {
                minEDate.setDate(minEDate.getDate() - 1);
            }
        }

//        var maxSDate = $scope.tasks[taskIndex].endDate;
//        var minEDate = $scope.tasks[taskIndex].startDate;
//        var maxEDate = taskIndex == $scope.tasks.length - 1 ? null : $scope.tasks[taskIndex + 1].startDate;
        var bounds = {index: taskIndex,
            start: {minDate: minSDate, maxDate: minEDate},
            end: {minDate: minSDate, maxDate: minEDate}
        };
        return bounds;
    };
}).controller('TaskController', function ($scope, $rootScope, swaAPIService) {
    /*Task controller*/
    $scope.today = function () {
        $scope.dt = new Date();
    };
    $scope.today();
    $scope.clear = function () {
        $scope.dt = null;
    };
    // Disable weekend selection
    $scope.disabled = function (date, mode) {
        return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
    };
    $scope.startOpened = false;
    $scope.endOpened = false;
    $scope.open = function ($event, flag) {
        $event.preventDefault();
        $event.stopPropagation();
        if (flag) {

            $scope.startOpened = true;
            $scope.endOpened = false;
        } else {

            $scope.startOpened = false;
            $scope.endOpened = true;
        }
    };
    $scope.dateOptions = {formatYear: 'yy', startingDay: 1};
    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[2];
    $scope.bounds = $rootScope.getDateBounds();
    $scope.newtask = {id: '', description: '', startDate: '', endDate: ''};
    $scope.readOnly = false;
    try {
        var task = $rootScope.getTask();
        if (task != null) {
            $scope.readOnly = true;
            if (typeof task.startDate === 'string' || task.startDate instanceof String) {
                var sD = task.startDate.split(".");
                task.startDate = new Date(sD[2], sD[1] - 1, sD[0]);
            }
            if (typeof task.endDate === 'string' || task.endDate instanceof String) {
                var eD = task.endDate.split(".");
                task.endDate = new Date(eD[2], eD[1] - 1, eD[0]);
            }
            $scope.newtask = {id: task.id, description: task.description, startDate: task.startDate, endDate: task.endDate};
        } else {
            $scope.readOnly = false;
        }
    } catch (e) {
    }
    $scope.taskStartDate = null;
    $scope.taskEndDate = null;
    $scope.submit4NewTask = function () {
        $scope.taskStartDate = null;
        $scope.taskEndDate = null;
        var task = $scope.newtask;
        var isOK = true;
        var boundDates = $rootScope.getDateBounds();
        if (task.startDate == null || task.startDate == '') {
            $scope.taskStartDate = 'Please select start date';
            isOK = false;
        }
        if (task.endDate == null || task.endDate == '') {
            $scope.taskEndDate = 'Please select end date';
            isOK = false;
        }
        if (isOK && boundDates.start.minDate && Date.parse(boundDates.start.minDate) > Date.parse(task.startDate) && task.id !== 1) {

            $scope.taskStartDate = 'Start Date should be greater than previous task end date';
            isOK = false;

        }
        if (isOK && boundDates.start.maxDate && Date.parse(boundDates.start.maxDate) < Date.parse(task.startDate)) {
            $scope.taskStartDate = 'Start Date should be less than next task start date';
            isOK = false;

        }
        if (isOK && boundDates.end.minDate && Date.parse(boundDates.end.minDate) > Date.parse(task.endDate)) {
            $scope.taskEndDate = 'End Date should be greater than previous task end date';
            isOK = false;

        }
        if (isOK && boundDates.end.maxDate && Date.parse(boundDates.end.maxDate) < Date.parse(task.endDate)) {
            $scope.taskEndDate = 'End Date should be less than next task start date';
            isOK = false;

        }
        if (isOK && Date.parse(task.endDate) <= Date.parse(task.startDate)) {
            $scope.taskEndDate = 'End Date should be greater than start date';
            isOK = false;

        }
        if (isOK) {
            swaAPIService.submitTask(task).then(
                    function (result, status, headers, config) {
                        if (result.data == 'Not Added') {
                            alert('Task \'' + task.description + '\' already existing!');
                        } else {
                            $rootScope.setTask(result.data);
                            try {
                                $('.modal-backdrop').remove();
                                $('#taskDialog').remove();
                            } catch (e) {
                            }
                        }
                    },
                    function (result, status, headers, config) {
                    }
            );
        }
    };
}).controller('UploadController', function ($scope, $rootScope, swaAPIService) {
    /*Upload controller*/
    $rootScope.banners = uploadBanners;
    $scope.message = null;
    $scope.submit2UploadBanner = function () {
        try {
            $scope.message = MESSAGES.BANNER_UPLOAD_PROGRESS;
            for (var i = 0; i < $scope.banners.length; i++) {
                var banner = $scope.banners[i];
                var file = banner.name;
                if (file && file.type == 'image/png') {
                    var saveAs = banner.saveAs;
                    var fd = new FormData();
                    fd.append('file', file);
                    fd.append('saveAs', saveAs);
                    swaAPIService.uploadImage(fd).then(
                            function (result, status, headers, config) {
                                $scope.message = MESSAGES.BANNER_UPLOAD_SUCCESS;
                            },
                            function (result, status, headers, config) {
                                $scope.message = MESSAGES.BANNER_UPLOAD_ERROR;
                            }
                    );
                } else {
                    $scope.message = MESSAGES.BANNER_UPLOAD_PNG_INFO;
                }
            }
        } catch (e) {
            $scope.message = e.toString();
        }
    };
}).controller('BannerController', function ($scope) {
    $scope.banners = viewBanners;
    $scope.interval = 5000;
}).controller('HomeController', function ($scope, $window, $location, authAPIService) {
    $scope.goHome = function () {
        $location.path(authAPIService.getURL());
    };
}).controller('SearchController', function ($scope, $window, dialogAPIService) {
    $scope.searchText = null;
    $scope.serachSCH = function () {
        if ($scope.searchText != null && $.trim($scope.searchText) != '') {
            var searchUrl = DIALOGS.SEARCH.TEMPLATE + $scope.searchText;
            dialogAPIService.createDialog(searchUrl, {
                id: 'frameSecrchDialog',
                title: DIALOGS.SEARCH.TITLE,
                top: 20,
                height: 1000,
                width: 1200,
                type: 'frame',
                text: 'Scholastic Search',
                backdrop: true,
                withFooter: false
            });
        } else {
            alert(MESSAGES.KEYWORD_INFO);
        }
    };
});


