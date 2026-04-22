let cache = { token: null, expiry: null };

export const getCache = () => cache;
export const setCache = (token, expiry) => { cache = { token, expiry }; };