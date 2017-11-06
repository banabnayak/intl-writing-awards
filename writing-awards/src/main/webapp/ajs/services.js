angular.module('SWAApp.services', []).factory('swaAPIService', function ($http, $q, $window, $location) {
    var swaAPI = {};
    var url = $window.location.pathname + 'rest';
    /* begin: public access*/
    swaAPI.initAppl = function () {
        var deferred = $q.defer();
        return $http.get(url + '/public/auth/launch').success(function (result, status, headers, config) {
            $window.sessionStorage.regExpired = result;
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };


    swaAPI.getGroups = function () {
        var deferred = $q.defer();
        return $http.get(url + '/public/classGroups').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.allSchools = function () {
        var deferred = $q.defer();
        return $http.get(url + '/public/schools/s').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.filterSchools = function (schoolName) {
        var deferred = $q.defer();
        return $http.get(url + '/public/schools/' + schoolName).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.submitEnrollmentForm = function (enrollForm) {
        var remParams = {transformRequest: angular.identity};
        var deferred = $q.defer();
        return $http.post(url + '/public/registration/save', JSON.stringify(enrollForm), remParams).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.request2ResetPwd = function (pwdForm) {
        var remParams = {transformRequest: angular.identity};
        var deferred = $q.defer();
        return $http.post(url + '/public/auth/resetpassword', JSON.stringify(pwdForm), remParams).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    /* end: public access*/
    /* begin: student access*/
    swaAPI.allTopics = function () {
        var deferred = $q.defer();
        return $http.get(url + '/student/topic').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.submitStoryForm = function (storyForm) {
        var remParams = {transformRequest: angular.identity};
        var deferred = $q.defer();
        return $http.post(url + '/student/story/save', JSON.stringify(storyForm), remParams).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.getUserStory = function () {
        var deferred = $q.defer();
        return $http.get(url + '/student/story/retrive').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.getActivities = function () {
        var deferred = $q.defer();
        return $http.get(url + '/student/story/activities').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.getOtherStories = function () {
        var deferred = $q.defer();
        return $http.get(url + '/student/story/assignments').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.getReviewQuestions = function () {
        var deferred = $q.defer();
        return $http.get(url + '/student/review/questions').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.submitReviews = function (reviewForm) {
        var deferred = $q.defer();
        var remParams = {transformRequest: angular.identity};
        return $http.post(url + '/student/review/submit', JSON.stringify(reviewForm), remParams).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    /* end: student access*/

    /* begin: admin access*/
    swaAPI.getTasks = function () {
        var deferred = $q.defer();
        return $http.get(url + '/admin/task').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.getState = function () {
        var deferred = $q.defer();
        return $http.get(url + '/public/state-city-list').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.deleteTask = function (id) {
        var deferred = $q.defer();
        return $http.get(url + '/admin/task/remove/' + id).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.submitTask = function (task) {
        var deferred = $q.defer();
        var remParams = {transformRequest: angular.identity};
        return $http.post(url + '/admin/task/add', JSON.stringify(task), remParams).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.submitTopic = function (topic) {
        var deferred = $q.defer();
        var remParams = {transformRequest: angular.identity};
        return $http.post(url + '/admin/topic/add', JSON.stringify(topic), remParams).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.deleteTopic = function (topicId, attemptFlag) {
        var deferred = $q.defer();
        var remParams = {transformRequest: angular.identity};
        return $http.get(url + '/admin/topic/remove/' + topicId + '/' + attemptFlag).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.getTopStories = function () {
        var deferred = $q.defer();
        return $http.get(url + '/admin/topStory').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };


    swaAPI.getTopParticipants = function () {
        var deferred = $q.defer();
        return $http.get(url + '/admin/topParticipant').success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };


    swaAPI.exportAsPdf = function (topStory, groupId) {
        var deferred = $q.defer();
        //var config = {headers: {'Accept': 'application/octet-stream;odata=verbose'},responseType:'arraybuffer'};
        var config = {headers: {'Accept': 'application/zip;odata=verbose'}, responseType: 'arraybuffer'};
        return $http.get(url + '/admin/zipExport/' + topStory + '/' + groupId, config).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.exportParticipantData = function (topParticipant, groupId) {
        var deferred = $q.defer();
        var config = {headers: {'Accept': 'text/csv;odata=verbose'}, responseType: 'arraybuffer'};
        return $http.get(url + '/admin/top-participants-report/' + groupId + '/' + topParticipant, config).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.exportStdents = function () {
        var deferred = $q.defer();
        var config = {headers: {'Accept': 'text/csv;odata=verbose'}, responseType: 'arraybuffer'};
        return $http.get(url + '/admin/csvExport', config).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.exportAllSchoolData = function () {
        var deferred = $q.defer();
        var config = {headers: {'Accept': 'text/csv;odata=verbose'}, responseType: 'arraybuffer'};
        return $http.get(url + '/admin/all-school-data', config).success(function (result) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.exportSchoolParticipated = function (is_participated) {
        var deferred = $q.defer();
        var config = {headers: {'Accept': 'text/csv;odata=verbose'}, responseType: 'arraybuffer'};
        return $http.get(url + '/admin/network-school/' + is_participated, config).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status, headers, config) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.exportNonNetworkSchool = function () {
        var deferred = $q.defer();
        var config = {headers: {'Accept': 'text/csv;odata=verbose'}, responseType: 'arraybuffer'};
        return $http.get(url + '/admin/non-network-school', config).success(function (result) {
            deferred.resolve(result);
        }).error(function (result, status) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };

    swaAPI.exportSubmissionDetails = function () {
        var deferred = $q.defer();
        var config = {headers: {'Accept': 'text/csv;odata=verbose'}, responseType: 'arraybuffer'};
        return $http.get(url + '/admin/submission-report', config).success(function (result, status, headers, config) {
            deferred.resolve(result);
        }).error(function (result, status) {
            deferred.resolve({status: status});
        });
        return deferred.promise;
    };
    swaAPI.uploadImage = function (fd) {
        var deferred = $q.defer();
        try {
            var remParams = {transformRequest: angular.identity, headers: {'Content-Type': undefined}};
            return $http.post(url + '/admin/image/upload', fd, remParams).success(function (result, status, headers, config) {
                deferred.resolve(result);
            }).error(function (result, status, headers, config) {
                deferred.resolve({status: status});
            });
        } catch (e) {
        }
        return deferred.promise;
    };
    /* and: admin access*/

    swaAPI.addAlert = function (message, type) {
        this.alerts = {};
        this.alerts[type] = this.alerts[type] || [];
        this.alerts[type].push(message);
        return this.alerts;
    };
    return swaAPI;
});