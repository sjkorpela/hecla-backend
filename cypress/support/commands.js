// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })

import { getCache, setCache } from './tokenCache';
import { config } from './config';

Cypress.Commands.add('getToken', () => {
  const { token, expiry } = getCache();

  if (token && expiry && Date.now() < expiry) {
    return cy.wrap(token);
  }

  return cy.request({
    method: 'POST',
    url: config.TOKEN_URL,
    form: true,
    body: {
      grant_type: 'client_credentials',
      client_id: config.CLIENT_ID,
      client_secret: config.CLIENT_SECRET
    },
  }).then((response) => {
    const { access_token, expires_in } = response.body;
    setCache(access_token, Date.now() + (expires_in - 10) * 1000);
    return access_token;
  });
});

Cypress.Commands.add('authRequest', (options) => {
  return cy.getToken().then((token) => {
    return cy.request({
      ...options,
      headers: {
        Authorization: `Bearer ${token}`,
        ...options.headers,
      },
    });
  });
});