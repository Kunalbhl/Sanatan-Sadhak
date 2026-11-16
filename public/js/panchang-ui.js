// Live Panchang widget: renders into every [data-panchang] element.
(function () {
  const A = window.SadhakAstro;
  const els = document.querySelectorAll('[data-panchang]');
  if (!A || !els.length) return;

  const CITIES = [
    ['New Delhi', 28.6139, 77.2090], ['Ayodhya', 26.7922, 82.1998], ['Varanasi', 25.3176, 82.9739],
    ['Ujjain', 23.1765, 75.7885], ['Jaipur', 26.9124, 75.7873], ['Jodhpur', 26.2389, 73.0243],
    ['Mumbai', 19.0760, 72.8777], ['Kolkata', 22.5726, 88.3639], ['Chennai', 13.0827, 80.2707],
    ['Bengaluru', 12.9716, 77.5946], ['Hyderabad', 17.3850, 78.4867], ['Ahmedabad', 23.0225, 72.5714],
    ['Pune', 18.5204, 73.8567], ['Lucknow', 26.8467, 80.9462], ['Patna', 25.5941, 85.1376],
    ['Bhopal', 23.2599, 77.4126], ['Haridwar', 29.9457, 78.1642], ['Guwahati', 26.1445, 91.7362],
    ['Puri', 19.8135, 85.8312], ['Dwarka', 22.2442, 68.9685], ['Rameswaram', 9.2876, 79.3129]
  ];
  const store = { get(k) { try { return localStorage.getItem(k); } catch (e) { return null; } }, set(k, v) { try { localStorage.setItem(k, v); } catch (e) {} } };
  const hi = () => document.documentElement.lang === 'hi';
  const T = (en, h) => hi() ? h : en;

  let place = (() => {
    const saved = store.get('ss_place');
    if (saved) { try { return JSON.parse(saved); } catch (e) {} }
    return { name: 'New Delhi', lat: 28.6139, lon: 77.2090, tz: 5.5 };
  })();
  let dayOffset = 0;

  function minutesInPlace(date, tz) { return ((date.getTime() / 60000 + tz * 60) % 1440 + 1440) % 1440; }
  function dayIndexInPlace(date, tz) { return Math.floor((date.getTime() / 60000 + tz * 60) / 1440); }

  function moonSVG(elong) {
    const r = 20, k = Math.cos(elong * Math.PI / 180), waxing = elong < 180;
    const rx = Math.abs(k) * r, outer = waxing ? 0 : 1, inner = (k > 0) === waxing ? 1 : 0;
    return `<svg class="moon-ic" viewBox="-24 -24 48 48" aria-hidden="true"><circle r="${r}" fill="#fdf3cf" stroke="#e9c46a"/><path d="M0,${-r} A${r},${r} 0 0 ${outer} 0,${r} A${rx},${r} 0 0 ${inner} 0,${-r} Z" fill="#5a3a2a" opacity=".85"/></svg>`;
  }

  function render(el) {
    const base = new Date();
    // The chosen civil date in the place's time zone, at noon
    const tz = place.tz != null ? place.tz : -base.getTimezoneOffset() / 60;
    const dIdx = dayIndexInPlace(base, tz) + dayOffset;
    const civil = new Date(dIdx * 86400000); // UTC date components = the place's civil date
    const local = new Date(civil.getUTCFullYear(), civil.getUTCMonth(), civil.getUTCDate(), 12);
    const p = A.panchang(local, { ...place, tz, now: dayOffset === 0 ? base : null });
    // "now" values only make sense for today
    const fmtEnd = (d) => {
      const di = dayIndexInPlace(d, tz) - dIdx;
      return A.fmtMin(minutesInPlace(d, tz)) + (di > 0 ? T(' (next day)', ' (अगले दिन)') : '');
    };
    const dateStr = civil.toLocaleDateString(hi() ? 'hi-IN' : 'en-IN', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', timeZone: 'UTC' });
    const paksha = p.shukla ? T('Shukla Paksha', 'शुक्ल पक्ष') : T('Krishna Paksha', 'कृष्ण पक्ष');
    const opts = CITIES.map(([n]) => `<option ${n === place.name ? 'selected' : ''}>${n}</option>`).join('');
    const ekadashi = (p.tithiNo === 11 || p.tithiNo === 26) ? `<span class="badge new">${T('Ekadashi vrat', 'एकादशी व्रत')}</span>` : '';
    const purnima = p.tithiNo === 15 ? `<span class="badge new">${T('Purnima', 'पूर्णिमा')}</span>` : p.tithiNo === 30 ? `<span class="badge new">${T('Amavasya', 'अमावस्या')}</span>` : '';

    el.innerHTML = `
      <div class="pc-top">
        <div><strong style="color:var(--maroon);font-size:1.15rem">${dateStr}</strong><br><span style="color:var(--muted);font-size:.9rem">${p.month} ${T('month (Purnimanta)', 'मास (पूर्णिमांत)')} · ${place.name} ${ekadashi} ${purnima}</span></div>
        <div style="display:flex;gap:8px;flex-wrap:wrap;align-items:center">
          <button class="chip" data-d="-1" aria-label="${T('Previous day', 'पिछला दिन')}">‹</button>
          <button class="chip ${dayOffset === 0 ? 'active' : ''}" data-d="0">${T('Today', 'आज')}</button>
          <button class="chip" data-d="1" aria-label="${T('Next day', 'अगला दिन')}">›</button>
          <select aria-label="${T('City', 'शहर')}" data-city>${place.name === 'My location' ? '<option selected>My location</option>' : ''}${opts}</select>
          <button class="chip" data-geo>📍 ${T('My location', 'मेरा स्थान')}</button>
        </div>
      </div>
      <div class="pc-grid">
        <div class="pc-item hl"><small>${T('Tithi', 'तिथि')}</small><strong>${hi() ? p.tithiHi : p.tithi}</strong><span>${paksha} · ${T('till', 'तक')} ${fmtEnd(p.tithiEnds)}</span></div>
        <div class="pc-item hl"><small>${T('Nakshatra', 'नक्षत्र')}</small><strong>${hi() ? p.nakHi : p.nak}</strong><span>${T('Pada', 'पद')} ${p.pada} · ${T('till', 'तक')} ${fmtEnd(p.nakEnds)}</span></div>
        <div class="pc-item"><small>${T('Yoga', 'योग')}</small><strong>${p.yoga}</strong><span>${T('at sunrise', 'सूर्योदय पर')}</span></div>
        <div class="pc-item"><small>${T('Karana', 'करण')}</small><strong>${p.karana}</strong><span>${T('at sunrise', 'सूर्योदय पर')}</span></div>
        <div class="pc-item"><small>🌅 ${T('Sunrise', 'सूर्योदय')}</small><strong>${A.fmtMin(p.sunrise)}</strong></div>
        <div class="pc-item"><small>🌇 ${T('Sunset', 'सूर्यास्त')}</small><strong>${A.fmtMin(p.sunset)}</strong></div>
        <div class="pc-item"><small>⛔ ${T('Rahu Kaal', 'राहु काल')}</small><strong>${A.fmtMin(p.rahu[0])} – ${A.fmtMin(p.rahu[1])}</strong></div>
        <div class="pc-item"><small>✨ ${T('Abhijit Muhurat', 'अभिजित मुहूर्त')}</small><strong>${A.fmtMin(p.abhijit[0])} – ${A.fmtMin(p.abhijit[1])}</strong></div>
        <div class="pc-item"><small>${T('Moon rashi', 'चंद्र राशि')}</small><strong>${p.moonRashi}</strong></div>
        <div class="pc-item"><small>${T('Sun rashi', 'सूर्य राशि')}</small><strong>${p.sunRashi}</strong></div>
        <div class="pc-item" style="display:flex;gap:10px;align-items:center">${moonSVG(p.elongNow)}<div><small>${T('Moon now', 'अभी चंद्र')}</small><strong>${Math.round(p.illum * 100)}%</strong><span>${hi() ? p.nakNowHi : p.nakNow}</span></div></div>
        <div class="pc-item"><small>${T('Ayanamsa', 'अयनांश')}</small><strong>${T('Lahiri', 'लाहिरी')} ${p.ayanamsa.toFixed(2)}°</strong><span>${T('sidereal', 'निरयन')}</span></div>
      </div>
      <p class="pc-note">${T('Tithi, nakshatra, yoga and karana are taken at local sunrise (Udaya). End times are estimates (±10 min). For major ceremonies, confirm with your purohit.', 'तिथि, नक्षत्र, योग और करण स्थानीय सूर्योदय (उदया) के अनुसार हैं। समाप्ति समय अनुमानित हैं (±10 मिनट)। बड़े अनुष्ठानों के लिए अपने पुरोहित से पुष्टि करें।')}</p>`;

    el.querySelectorAll('[data-d]').forEach((b) => b.addEventListener('click', () => {
      const d = +b.dataset.d; dayOffset = d === 0 ? 0 : dayOffset + d; renderAll();
    }));
    el.querySelector('[data-city]').addEventListener('change', (e) => {
      const c = CITIES.find(([n]) => n === e.target.value);
      if (c) { place = { name: c[0], lat: c[1], lon: c[2], tz: 5.5 }; store.set('ss_place', JSON.stringify(place)); renderAll(); }
    });
    el.querySelector('[data-geo]').addEventListener('click', () => {
      if (!navigator.geolocation) return window.showToast && showToast(T('Location is not available', 'स्थान उपलब्ध नहीं है'));
      navigator.geolocation.getCurrentPosition((pos) => {
        place = { name: 'My location', lat: +pos.coords.latitude.toFixed(3), lon: +pos.coords.longitude.toFixed(3), tz: -new Date().getTimezoneOffset() / 60 };
        store.set('ss_place', JSON.stringify(place)); renderAll();
        window.showToast && showToast(T('Panchang updated for your location', 'आपके स्थान के लिए पंचांग अपडेट हुआ'));
      }, () => window.showToast && showToast(T('Location permission was denied', 'स्थान की अनुमति नहीं मिली')), { timeout: 10000 });
    });
  }
  function renderAll() { els.forEach(render); }
  renderAll();
  setInterval(renderAll, 60000);
  document.addEventListener('langchange', renderAll);
})();
