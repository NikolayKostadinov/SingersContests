(function () {
    const birthElement = document.getElementById("birthDay");

    birthElement.addEventListener('change', getAgeGroup);

    async function getAgeGroup(event) {
        try {
            event.preventDefault();
            const url = this.dataset.url + '/' + this.value;

            let response = await fetch(url);
            let data = await response.json();

            if (response.ok) {
                showData(data);
            } else {
                showError(data)
            }
        } catch (e) {
            const idElement = document.getElementById('age-group');
            idElement.textContent = '';
            showError();
        }
    }

    function showData(data) {
        const idElement = document.getElementById('age-group');
        idElement.textContent = data.name + ' age group';
    }

    function showError(data) {
        const errModal = new bootstrap.Modal('#errorModal', {keyboard: true})
        document.getElementById('error-message').textContent = data.message ? data.message : 'Operation failed!';
        errModal.show();
    }
})()
