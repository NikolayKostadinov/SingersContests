// attach event handlers after form loads
(function () {
    function attachEventHandlers() {
        document.getElementsByName("btn-add")
            .forEach(element => {
                element.addEventListener("click", addButtonClick);
            })

        document.getElementsByName("btn-remove")
            .forEach(element => {
                element.addEventListener("click", removeButtonClick);
            })
    }

    attachEventHandlers();

    function addButtonClick(event) {
        event.preventDefault();
        let selectedManagerElement = document.querySelector('[name="manager-row"]:last-of-type');
        if (selectedManagerElement) {
            let managerIndex = Number(selectedManagerElement.dataset.manager);
            let options = selectedManagerElement.getElementsByTagName("select")[0].options;
            let nextManagerSelector = createElement(managerIndex + 1, options);
            selectedManagerElement.parentElement.append(nextManagerSelector);
            attachEventHandlers();
        }
    }


    function removeButtonClick(event) {
        event.preventDefault();
        let managerIndex = Number(event.currentTarget.dataset.id);
        let selectedManagerElement = document.querySelector('[data-manager = "' + managerIndex + '"]');
        selectedManagerElement.parentElement.remove(selectedManagerElement);
    }

    function createElement(index, options) {
        let html = "<div class=\"form-row\" name=\"manager-row\" data-manager=\"" + index + "\">\n" +
            "                <div class=\"row\">\n" +
            "                    <div class=\"col-10\">\n" +
            "                        <select class=\"form-control\" id=\"managers" + index + "\" name=\"managers[" + index + "]\">\n" +
            "                        </select>\n" +
            "                    </div>\n" +
            "                    <div class=\"col-2\">\n" +
            "                        <button name=\"btn-add\" class=\"btn btn-sm btn-primary\" data-id=\"" + index + "\"><i class=\"bi bi-plus-square\"></i></button>\n" +
            "                        <button name=\"btn-remove\" class=\"btn btn-sm btn-danger\" data-id=\"" + index + "\"><i class=\"bi bi-x-square\"></i></button>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";

        let managerElement = document.createRange().createContextualFragment(html);
        let select = managerElement.querySelector("select");
        for (let i = 0; i < options.length; i++) {
            let option = options[i].cloneNode(true);
            option.selected = false;
            select.appendChild(option);
        }

        return managerElement;
    }

})();

function createElement() {
    document.createRange().createContextualFragment(html);
}
