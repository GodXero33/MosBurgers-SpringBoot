const SHOP_WINDOW = {
	db_host: 'http://localhost:5500',
	init_content: 2
};
const SHOP_TEST = {
	auto_loggin: false
};



function newStyleSheet (url, name) {
	return new Promise(async (res, rej) => {
		try {
			const response = await fetch(`${url}?t=${new Date().getTime()}`);
			
			if (!response.ok) throw new Error('Failed to load style sheet. ' + url);

			const css = await response.text();

			const style = document.createElement('style');
			style.setAttribute('mos-style-name', name);
			style.innerHTML = css;
			document.head.appendChild(style);
			res();
		} catch (error) {
			rej(error);
		}
	});
}

function deleteStyleSheet (name) {
	const style = document.head.querySelector(`style[mos-style-name="${name}"]`);
	style.remove();
}

function updateThemeMode (mode) {
	if (mode == 'light') {
		document.getElementById('theme-mode-switch-ghost-input').checked = true;
	} else {
		document.getElementById('theme-mode-switch-ghost-input').checked = false;
	}
}

function updateLayoutStyles () {
	if (!SHOP_WINDOW['user_style']) return;

	const { theme, mode } = SHOP_WINDOW['user_style'];

	updateThemeMode(mode);
	document.querySelector(':root').style.setProperty('--theme-hue', `${theme}deg`);
}

function loadDynamicSrcipt (url) {
	return new Promise((res, rej) => {
		try {
			const script = document.createElement('script');
			script.type = 'text/javascript';
			script.async = true;
			script.src = `${url}?t=${new Date().getTime()}`;

			script.addEventListener('load', () => {
				res({ ok: true });
			});

			script.addEventListener('error', () => {
				rej({ ok: false });
			});

			document.head.appendChild(script);
		} catch (error) {
			rej({ ok: false });
		}
	});
}

function sendWarningAlert (message) {
	alert(message);
}

function sendInfoAlert (message) {
	alert(message);
}

function wait (time) {
	return new Promise(res => setTimeout(res, time));
}

window.addEventListener('load', async () => {
	SHOP_WINDOW['main-container'] = document.getElementById('main-container');
	SHOP_WINDOW['loader'] = document.getElementById('loader');

	// Ghost input for change theme
	const ghostInput = document.createElement('input');
	ghostInput.setAttribute('id', 'theme-mode-switch-ghost-input');
	ghostInput.setAttribute('type', 'checkbox');
	document.body.appendChild(ghostInput);

	try {
		await loadDynamicSrcipt('js/login.js');
	} catch (error) {
		console.error(error);
	}
});
