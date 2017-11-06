
$(function () {
    $('.popupText').popover(); // { trigger: "hover"}
    $('[data-toggle="popover"]').popover();

    $('body').on('click', function (e) {
        $('[data-toggle="popover"]').each(function () {
            if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                $(this).popover('hide');
            }
        });
    });
    $(document.body).on('click', '.dropdown-menu li', function (event) {

        var $target = $(event.currentTarget);

        $target.closest('.btn-group')
                .find('[data-bind="label"]').text($target.text())
                .end()
                .children('.dropdown-toggle').dropdown('toggle');

        return false;

    });

    String.prototype.capitalize = function () {
        return this.replace(/(^|\s)([a-z])/g, function (m, p1, p2) {
            return p1 + p2.toUpperCase();
        });
    };

    Array.prototype.contains = function (obj) {
        var i = this.length;
        while (i--) {
            if (this[i].toLowerCase() === obj.toLowerCase()) {
                return true;
            }
        }
        return false;
    };

    Array.prototype.containsObjVal = function (val, prop) {
        for (var i = 0; i < this.length; i++) {
            if (this[i][prop].toLowerCase() === val.toLowerCase()) {
                return true;
            }
        }
        return false;
    };
});

