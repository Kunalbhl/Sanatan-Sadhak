// Vedic astronomy for the website, the same engine as the app (VedicEphemeris.kt).
// Sun and Moon from Schlyter's theory (about 1–2'), sidereal via the Lahiri ayanamsa.
(function (g) {
  const R = Math.PI / 180;
  const norm = (x) => ((x % 360) + 360) % 360;
  const sin = (d) => Math.sin(d * R), cos = (d) => Math.cos(d * R);

  function jdFromDate(date) { return date.getTime() / 86400000 + 2440587.5; }
  function lahiri(jd) { return 23.853 + (jd - 2451545.0) / 365.25 * 0.013964; }

  function tropical(jd) {
    const d = jd - 2451543.5;
    const ws = 282.9404 + 4.70935e-5 * d, es = 0.016709 - 1.151e-9 * d, ms = norm(356.0470 + 0.9856002585 * d);
    const Es = ms + (es / R) * sin(ms) * (1 + es * cos(ms));
    const sun = norm(Math.atan2(Math.sqrt(1 - es * es) * sin(Es), cos(Es) - es) / R + ws);

    const N = norm(125.1228 - 0.0529538083 * d), i = 5.1454, w = norm(318.0634 + 0.1643573223 * d), e = 0.0549, M = norm(115.3654 + 13.0649929509 * d);
    let E = M + (e / R) * sin(M) * (1 + e * cos(M));
    for (let k = 0; k < 8; k++) E -= (E - (e / R) * sin(E) - M) / (1 - e * cos(E));
    const xv = cos(E) - e, yv = Math.sqrt(1 - e * e) * sin(E);
    const v = Math.atan2(yv, xv) / R, r = Math.hypot(xv, yv);
    const xh = r * (cos(N) * cos(v + w) - sin(N) * sin(v + w) * cos(i));
    const yh = r * (sin(N) * cos(v + w) + cos(N) * sin(v + w) * cos(i));
    let lon = Math.atan2(yh, xh) / R;
    const Ls = norm(ms + ws), Lm = norm(M + w + N), D = norm(Lm - Ls), F = norm(Lm - N);
    lon += -1.274 * sin(M - 2 * D) + 0.658 * sin(2 * D) - 0.186 * sin(ms) - 0.059 * sin(2 * M - 2 * D)
      - 0.057 * sin(M - 2 * D + ms) + 0.053 * sin(M + 2 * D) + 0.046 * sin(2 * D - ms) + 0.041 * sin(M - ms)
      - 0.035 * sin(D) - 0.031 * sin(M + ms) - 0.015 * sin(2 * F - 2 * D) + 0.011 * sin(M - 4 * D);
    return { sun, moon: norm(lon) };
  }

  // Sunrise/sunset in local minutes (NOAA approximation, the same as the app)
  function sunTimes(date, lat, lon, tzHours) {
    const start = new Date(date.getFullYear(), 0, 0);
    const doy = Math.floor((date - start) / 86400000);
    const gm = 2 * Math.PI * (doy - 1) / 365;
    const eqt = 229.18 * (0.000075 + 0.001868 * Math.cos(gm) - 0.032077 * Math.sin(gm) - 0.014615 * Math.cos(2 * gm) - 0.040849 * Math.sin(2 * gm));
    const decl = 0.006918 - 0.399912 * Math.cos(gm) + 0.070257 * Math.sin(gm) - 0.006758 * Math.cos(2 * gm) + 0.000907 * Math.sin(2 * gm) - 0.002697 * Math.cos(3 * gm) + 0.00148 * Math.sin(3 * gm);
    const la = lat * R;
    const cosH = (Math.sin(-0.833 * R) - Math.sin(la) * Math.sin(decl)) / (Math.cos(la) * Math.cos(decl));
    const H = cosH >= 1 ? 0 : cosH <= -1 ? Math.PI : Math.acos(cosH);
    const noon = 720 - 4 * lon - eqt + 60 * tzHours;
    return { sunrise: noon - 4 * H / R, sunset: noon + 4 * H / R, noon };
  }

  const NAK = ['Ashwini', 'Bharani', 'Krittika', 'Rohini', 'Mrigashira', 'Ardra', 'Punarvasu', 'Pushya', 'Ashlesha', 'Magha', 'Purva Phalguni', 'Uttara Phalguni', 'Hasta', 'Chitra', 'Swati', 'Vishakha', 'Anuradha', 'Jyeshtha', 'Mula', 'Purva Ashadha', 'Uttara Ashadha', 'Shravana', 'Dhanishta', 'Shatabhisha', 'Purva Bhadrapada', 'Uttara Bhadrapada', 'Revati'];
  const NAK_HI = ['अश्विनी', 'भरणी', 'कृत्तिका', 'रोहिणी', 'मृगशिरा', 'आर्द्रा', 'पुनर्वसु', 'पुष्य', 'आश्लेषा', 'मघा', 'पूर्व फाल्गुनी', 'उत्तर फाल्गुनी', 'हस्त', 'चित्रा', 'स्वाती', 'विशाखा', 'अनुराधा', 'ज्येष्ठा', 'मूल', 'पूर्वाषाढ़ा', 'उत्तराषाढ़ा', 'श्रवण', 'धनिष्ठा', 'शतभिषा', 'पूर्व भाद्रपद', 'उत्तर भाद्रपद', 'रेवती'];
  const TITHI = ['Pratipada', 'Dwitiya', 'Tritiya', 'Chaturthi', 'Panchami', 'Shashthi', 'Saptami', 'Ashtami', 'Navami', 'Dashami', 'Ekadashi', 'Dwadashi', 'Trayodashi', 'Chaturdashi'];
  const TITHI_HI = ['प्रतिपदा', 'द्वितीया', 'तृतीया', 'चतुर्थी', 'पंचमी', 'षष्ठी', 'सप्तमी', 'अष्टमी', 'नवमी', 'दशमी', 'एकादशी', 'द्वादशी', 'त्रयोदशी', 'चतुर्दशी'];
  const YOGA = ['Vishkambha', 'Priti', 'Ayushman', 'Saubhagya', 'Shobhana', 'Atiganda', 'Sukarma', 'Dhriti', 'Shula', 'Ganda', 'Vriddhi', 'Dhruva', 'Vyaghata', 'Harshana', 'Vajra', 'Siddhi', 'Vyatipata', 'Variyan', 'Parigha', 'Shiva', 'Siddha', 'Sadhya', 'Shubha', 'Shukla', 'Brahma', 'Indra', 'Vaidhriti'];
  const KARANA_MOV = ['Bava', 'Balava', 'Kaulava', 'Taitila', 'Garaja', 'Vanija', 'Vishti'];
  const RASHI = ['Mesha', 'Vrishabha', 'Mithuna', 'Karka', 'Simha', 'Kanya', 'Tula', 'Vrishchika', 'Dhanu', 'Makara', 'Kumbha', 'Meena'];
  const MONTH = ['Chaitra', 'Vaishakha', 'Jyeshtha', 'Ashadha', 'Shravana', 'Bhadrapada', 'Ashwin', 'Kartik', 'Margashirsha', 'Pausha', 'Magha', 'Phalguna'];
  const RAHU = [8, 2, 7, 5, 6, 4, 3]; // octet of the day, Sunday..Saturday

  function karanaName(n) { // n = 1..60
    if (n === 1) return 'Kimstughna';
    if (n >= 58) return ['Shakuni', 'Chatushpada', 'Naga'][n - 58];
    return KARANA_MOV[(n - 2) % 7];
  }

  function limbs(jd) {
    const t = tropical(jd), ay = lahiri(jd);
    const sun = norm(t.sun - ay), moon = norm(t.moon - ay), elong = norm(t.moon - t.sun);
    const t2 = tropical(jd + 1 / 24);
    const eRate = Math.max(0.3, norm(norm(t2.moon - t2.sun) - elong + 180) - 180);
    const mRate = Math.max(0.3, norm(t2.moon - t.moon + 180) - 180);
    const span = 360 / 27;
    const nm = tropical(jd - elong / 12.19);
    const amanta = (Math.floor(norm(nm.sun - ay) / 30) + 1) % 12;
    return {
      sun, moon, elong, ayanamsa: ay,
      tithi: Math.floor(elong / 12) + 1,
      nak: Math.floor(moon / span), pada: Math.floor((moon % span) / span * 4) + 1,
      yoga: Math.floor(norm(sun + moon) / span),
      karana: Math.floor(elong / 6) + 1,
      tithiEndsH: (12 - elong % 12) / eRate,
      nakEndsH: (span - moon % span) / mRate,
      amanta, illum: (1 - cos(elong)) / 2
    };
  }

  function panchang(date, place) {
    const tz = -date.getTimezoneOffset() / 60;
    const tzPlace = place.tz != null ? place.tz : tz;
    const st = sunTimes(date, place.lat, place.lon, tzPlace);
    const midnightUtcMs = Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()) - tzPlace * 3600000;
    const sunriseDate = new Date(midnightUtcMs + st.sunrise * 60000);
    const jdRise = jdFromDate(sunriseDate);
    const atRise = limbs(jdRise);
    const now = limbs(jdFromDate(place.now || date));
    const dayLen = st.sunset - st.sunrise, oct = dayLen / 8, mu = dayLen / 15;
    const wd = new Date(midnightUtcMs + 12 * 3600000).getUTCDay();
    const rahu = st.sunrise + (RAHU[wd] - 1) * oct;
    const shukla = atRise.tithi <= 15;
    const ti = shukla ? atRise.tithi : atRise.tithi - 15;
    const month = shukla ? atRise.amanta : (atRise.amanta + 1) % 12;
    return {
      place, tz: tzPlace, sunrise: st.sunrise, sunset: st.sunset,
      rahu: [rahu, rahu + oct], abhijit: [st.sunrise + 7 * mu, st.sunrise + 8 * mu],
      tithiNo: atRise.tithi, shukla,
      tithi: ti === 15 ? (shukla ? 'Purnima' : 'Amavasya') : TITHI[ti - 1],
      tithiHi: ti === 15 ? (shukla ? 'पूर्णिमा' : 'अमावस्या') : TITHI_HI[ti - 1],
      tithiEnds: new Date(sunriseDate.getTime() + atRise.tithiEndsH * 3600000),
      nak: NAK[atRise.nak], nakHi: NAK_HI[atRise.nak], pada: atRise.pada,
      nakEnds: new Date(sunriseDate.getTime() + atRise.nakEndsH * 3600000),
      nakNow: NAK[now.nak], nakNowHi: NAK_HI[now.nak],
      yoga: YOGA[atRise.yoga], karana: karanaName(atRise.karana),
      moonRashi: RASHI[Math.floor(atRise.moon / 30)], sunRashi: RASHI[Math.floor(atRise.sun / 30)],
      month: MONTH[month], ayanamsa: atRise.ayanamsa, illum: now.illum, elongNow: now.elong
    };
  }

  function fmtMin(min) {
    min = ((Math.round(min) % 1440) + 1440) % 1440;
    let h = Math.floor(min / 60), m = min % 60, ap = h >= 12 ? 'PM' : 'AM';
    h = h % 12 || 12;
    return `${h}:${String(m).padStart(2, '0')} ${ap}`;
  }

  g.SadhakAstro = { tropical, limbs, panchang, sunTimes, lahiri, jdFromDate, fmtMin, norm };
})(window);
