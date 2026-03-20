
describe('Persons API', () => {

  it('GET /api/persons returns 401 without authentication', () => {
  cy.request({
    method: 'GET',
    url: '/api/persons',
    failOnStatusCode: false,
  }).then((response) => {
    expect(response.status).to.eq(401);
  });
  });

  it('GET /api/persons returns 200', () => {
    cy.authRequest({ method: 'GET', url: '/api/persons' }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });

  it('DELETE /api/persons/:id returns 204', () => {
    cy.authRequest({ method: 'DELETE', url: '/api/persons/1' }).then((response) => {
      expect(response.status).to.eq(204);
    });
  });

});