(function () {
    /*
    * Add event listener for each jury member button
    * */

    let juryMemberLinks = document.querySelectorAll('.jury-member');

    Array.from(juryMemberLinks).forEach(link => {
        link.addEventListener('click', loadJuryMemberData);
    });

    async function loadJuryMemberData(event) {
        event.preventDefault();
        let response = await fetch(this.href);
        let data = await response.json();

        if (response.ok) {
            showData(data);
        } else {
            showError(data)
        }
    }

    function showData(data) {
        const idElement = document.getElementById('id');
        const nameElement = document.getElementById('name');
        const detailsElement = document.getElementById('details');
        const pictureHolderElement = document.getElementById('picture-holder');
        const imageUrlElement = document.getElementById('imageUrl');

        idElement.value = data.id;
        nameElement.innerText = `Edit ${data.fullName}'s Details`;
        detailsElement.value = data.details;
        pictureHolderElement.src = data.imageUrl;
        imageUrlElement.value = data.imageUrl;
    }

    function showError(data) {
        const errModal = new bootstrap.Modal('#errorModal', {keyboard: true})
        document.getElementById('error-message').textContent = data.message ? data.message : 'Operation failed!';
        errModal.show();
    }
})();

