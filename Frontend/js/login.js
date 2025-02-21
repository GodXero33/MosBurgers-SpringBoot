(function () {
	function inputResetValidity (event) {
		event.target.setCustomValidity('');
		event.target.reportValidity();
	}

	async function loginClick (event) {
		const userNameInput = document.getElementById('user-name-input');
		const passwordInput = document.getElementById('password-input');
		const userName = userNameInput.value;
		const password = passwordInput.value;

		if (userName == '') {
			userNameInput.setCustomValidity('User Name can\'t be empty!');
			userNameInput.reportValidity();
			return;
		}

		event.target.disabled = true;
		let user;

		try {
			const response = await fetch(`${SHOP_WINDOW.db_host}/admin/${userName}`);

			if (!response.ok) throw new Error('Failed to fetch admins.');

			user = await response.json();
		} catch (error) {
			console.error(error);
			alert('Hey, Somthing went wrong. Please try again!');
			event.target.disabled = false;
			return;
		}

		if (!user.ok) {
			userNameInput.setCustomValidity('User Name is not found!');
			userNameInput.reportValidity();
			event.target.disabled = false;
			return;
		}

		if (password == '') {
			passwordInput.setCustomValidity('Invalid Password!');
			passwordInput.reportValidity();
			event.target.disabled = false;
			return;
		}

		try {
			const response = await fetch(`${SHOP_WINDOW.db_host}/admin/${userName}/${password}`);

			if (!response.ok) throw new Error('Failed to fetch admins.');

			user = await response.json();
		} catch (error) {
			console.error(error);
			alert('Hey, Somthing went wrong. Please try again!');
			event.target.disabled = false;
			return;
		}

		if (!user.ok) {
			passwordInput.setCustomValidity('Invalid Password!');
			passwordInput.reportValidity();
			event.target.disabled = false;
			return;
		}

		loginSuccess(user);
	}

	function releaseMemory () {
		document.getElementById('login-btn').removeEventListener('click', loginClick);
		document.getElementById('user-name-input').removeEventListener('input', inputResetValidity);
		document.getElementById('password-input').removeEventListener('input', inputResetValidity);

		loginClick = null;
		inputResetValidity = null;
		loginSuccess = null;
		releaseMemory = null;
	}

	function loadUserSettings (adimId) {
		return new Promise((res, rej) => {
			try {
				res({
					style: {
						theme: 0,
						mode: 'dark'
					},
					initialPage: 1
				});
			} catch (error) {
				rej(error);
			}
		});
	}

	async function loginSuccess (user) {
		releaseMemory();
		document.title = `Mos Burger - ${user['name']}`;
		SHOP_WINDOW['admin'] = user;
		SHOP_WINDOW['loader'].classList.remove('hide');

		try {
			const userSettings = await loadUserSettings(user['admin_id']);
			SHOP_WINDOW['user_style'] = userSettings.style;
			SHOP_WINDOW['init_content'] = userSettings.initialPage;
			updateLayoutStyles();

			await loadDynamicSrcipt('js/content.js');
			// console.log(userSettings);
		} catch (error) {
			console.error(error);
		}
	}

	async function init () {
		try {
			await newStyleSheet('components/login/login.css', 'login');
		} catch (error) {
			console.error(error);
		}

		SHOP_WINDOW['loader'].classList.add('hide');
	
		document.getElementById('login-btn').addEventListener('click', loginClick);
		document.getElementById('user-name-input').addEventListener('input', inputResetValidity);
		document.getElementById('password-input').addEventListener('input', inputResetValidity);

		if (SHOP_TEST['auto_loggin']) {
			document.getElementById('user-name-input').value = 'GodXero';
			document.getElementById('password-input').value = '1234';
			document.getElementById('login-btn').click();
		}
	}

	async function createLogin () {
		try {
			const response = await fetch('components/login/login.html', { cache: 'no-cache' });

			if (!response.ok) throw new Error('Failed to fetch');

			const html = await response.text();
			SHOP_WINDOW['main-container'].innerHTML = html;
			init();
		} catch (error) {
			console.error(error);
		}
	}

	createLogin();
})();
