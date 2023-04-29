const likeButton = document.getElementById('like-a');
const dislikeButton = document.getElementById('dislike-a');
const url = 'http://localhost:8080/answers/';

likeButton.onclick = () => {
    const aId = document.getElementById('a-id').textContent;
    fetch(url + aId + '/like', {
        method: 'PATCH',
    });
};

dislikeButton.onclick = () => {
    const aId = document.getElementById('a-id').textContent;
    fetch(url + aId + '/dislike', {
        method: 'PATCH',
    });
};