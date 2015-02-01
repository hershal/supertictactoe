function jump_to_session(session_id) {

    console.log("Jumping to session: " + session_id);

    var pathname = window.location.pathname;
    var new_href = window.location.pathname + '#' + session_id;

    window.location = new_href;
    window.location.reload();
}

function get_current_session_id() {

    return window.location.hash.replace(/#/g, '');
}

function init_session_id() {

    var ref = new Firebase('https://sizzling-torch-8770.firebaseIO.com/games');
    var hash = get_current_session_id();

    if (hash) {
        ref = ref.child(hash);
    } else {
        /* generate unique location. */
        ref = ref.push();
        /* add it as a hash to the URL. */
        window.location = window.location.pathname + '#' + ref.name().replace(/^-/g,'');
    }

    if (typeof console !== 'undefined')
        console.log('Firebase data: ', ref.toString());

    return ref;
}

function hash_changed() {

    window.location = document.location.href;
    window.location.reload();
};
