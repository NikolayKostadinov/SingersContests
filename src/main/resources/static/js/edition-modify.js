// attach event handlers after form loads
(function () {
    function attachEventHandlers() {
        document.getElementById("add-category")
            .addEventListener("click", addCategoryButtonClick);

        document.getElementsByName("remove-category")
            .forEach(element =>
                element.addEventListener("click", removeCategoryButtonClick));

        document.getElementById("add-age-group")
            .addEventListener("click", addAgeGroupButtonClick);

        document.getElementsByName("remove-group")
            .forEach(element =>
                element.addEventListener("click", removeAgeGroupButtonClick));
    }

    attachEventHandlers();

    function addCategoryButtonClick(event) {
        event.preventDefault();
        let selectedManagerElement = document.querySelector('[name="category-row"]:last-of-type');
        if (selectedManagerElement) {
            let managerIndex = Number(selectedManagerElement.dataset.index);
            let nextManagerSelector = createCategoryElement(managerIndex + 1);
            selectedManagerElement.parentElement.append(nextManagerSelector);
            attachEventHandlers();
        }
    }


    function removeCategoryButtonClick(event) {
        event.preventDefault();
        let index = Number(event.currentTarget.dataset.id);
        document.getElementsByName('performanceCategories[' + index + '].name')[0].value = "Empty";
        document.getElementById('performanceCategories' + index + '.deleted').value = true;
        document.querySelector('[data-index = "' + index + '"]').hidden = true;
    }

    function createCategoryElement(index) {
        let html = "<div class=\"row align-middle\" name=\"category-row\" data-index=\"" + index + "\">\n" +
            "                <div class=\"col-9\">\n" +
            "                    <div class=\"form-row\">\n" +
            "                        <input id=\"category-name\" type=\"text\" class=\"form-control\" placeholder=\"Category Name\" name=\"performanceCategories[" + index + "].name\" value=\"\">\n" +
            "                        <div class=\"invalid-feedback\">Name is required!</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"col-2\">\n" +
            "                    <div class=\"form-check\">\n" +
            "                        <input class=\"form-check-input\" type=\"checkbox\" id=\"performanceCategories" + index + ".required\" name=\"performanceCategories[" + index + "].required\" value=\"true\"><input type=\"hidden\" name=\"_performanceCategories[0].required\" value=\"on\">\n" +
            "                        <label class=\"form-check-label\">\n" +
            "                            Required\n" +
            "                        </label>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <input type=\"hidden\" id=\"performanceCategories" + index + ".deleted\" name=\"performanceCategories[" + index + "].deleted\" value=\"false\">\n" +
            "                <div class=\"col-1\">\n" +
            "                    <button id=\"performanceCategories" + index + ".deleted\"  type=\"button\" class=\"btn-close\" name=\"btn-remove\" data-id='" + index + "'></button>\n" +
            "                </div>\n" +
            "            </div>";

        return document.createRange().createContextualFragment(html);
    }

    function addAgeGroupButtonClick(event) {
        event.preventDefault();
        let selectedManagerElement = document.querySelector('[name="age-row"]:last-of-type');
        if (selectedManagerElement) {
            let managerIndex = Number(selectedManagerElement.dataset.index);
            let nextManagerSelector = createAgeGroupElement(managerIndex + 1);
            selectedManagerElement.parentElement.append(nextManagerSelector);
            attachEventHandlers();
        }
    }


    function removeAgeGroupButtonClick(event) {
        event.preventDefault();
        let index = Number(event.currentTarget.dataset.id);
        document.getElementsByName('ageGroups[' + index + '].name')[0].value = "Empty";
        document.getElementsByName('ageGroups[' + index + '].minAge')[0].value = 0;
        document.getElementsByName('ageGroups[' + index + '].maxAge')[0].value = 0;
        document.getElementById('ageGroups' + index + '.deleted').value = true;
        document.querySelector('[data-index = "' + index + '"]').hidden = true;
    }

    function createAgeGroupElement(index) {
        let html = "<div class=\"row align-middle\" name=\"age-row\" data-index=\"" + index + "\">\n" +
            "                <div class=\"col-7\">\n" +
            "                    <div class=\"form-row\">\n" +
            "                        <input type=\"text\" class=\"form-control\" placeholder=\"Age Group Name\" id=\"ageGroups" + index + ".name\" name=\"ageGroups[" + index + "].name\" value=\"\">\n" +
            "                        \n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"col-2\">\n" +
            "                    <div class=\"form-row\">\n" +
            "                        <input type=\"number\" class=\"form-control\" placeholder=\"Min Age\" id=\"ageGroups" + index + ".minAge\" name=\"ageGroups[" + index + "].minAge\" value=\"\">\n" +
            "                        \n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"col-2\">\n" +
            "                    <div class=\"form-row\">\n" +
            "                        <input type=\"number\" class=\"form-control\" placeholder=\"Max Age\" id=\"ageGroups" + index + ".maxAge\" name=\"ageGroups[" + index + "].maxAge\" value=\"\">\n" +
            "                        \n" +
            "                    </div>\n" +
            "                </div>\n" +
            "\n" +
            "                <input type=\"hidden\" id=\"ageGroups" + index + ".deleted\" name=\"ageGroups[" + index + "].deleted\" value=\"false\">\n" +
            "                <div class=\"col-1\">\n" +
            "                    <a href=\"#\" id=\"categories" + index + "\" type=\"button\" class=\"btn-close\" name=\"remove-group\" data-id=\"" + index + "\"></a>\n" +
            "                </div>\n" +
            "            </div>";

        return document.createRange().createContextualFragment(html);
    }

})();
