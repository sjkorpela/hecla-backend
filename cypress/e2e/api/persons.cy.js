
describe("Persons API", () => {

  /*
    Tests for:
    1.  GET /api/persons returns 401 without authentication
    2.  GET /api/persons returns 200 with authentication

    3.  POST /api/persons returns 401 without authentication
    4.  POST /api/persons returns 201 with authentication, created id is saved

    5.  GET /api/persons/id returns 401 without authentication
    6.  GET /api/persons/id returns 200 with authentication

    7.  PUT /api/persons/id returns 401 without authentication
    8.  PUT /api/persons/id returns 200 with authentication

    9.  DELETE /api/persons/id returns 401 without authentication
    10. DELETE /api/persons/id returns 204 with authentication
    11. GET /api/persons/id returns 404 after DELETE request

    12. POST /api/persons return 400 with invalid parent id
  */

  // id of posted person, saved so that all tests happen on same person, that is also deleted after testing
  let createdId;


  it("GET /api/persons returns 401 without authentication", () => {
    cy.request({
      method: "GET",
      url: "/api/persons",
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(401);
    });
  });

  it("GET /api/persons returns 200", () => {
    cy.authRequest({ method: "GET", url: "/api/persons" }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });



  it("POST /api/persons returns 401 without authentication", () => {
    cy.fixture("erkkiJokinen").then((person) => {
      cy.request({
        method: "POST",
        url: "/api/persons",
        failOnStatusCode: false,
        body: person,
      }).then((response) => {
        expect(response.status).to.eq(401);
      });
    });
  });

  it("POST /api/persons returns 201 with authentication, created id is saved", () => {
    cy.fixture("erkkiJokinen").then((person) => {
      cy.authRequest({
        method: "POST",
        url: "/api/persons",
        body: person,
      }).then((response) => {
        expect(response.status).to.eq(201);

        expect(response.body.birthPlace).to.eq(person.birthPlace);
        expect(response.body.deathPlace).to.eq(person.deathPlace);

        createdId = response.body.id;
      });
    });
  });



  it("GET /api/persons/id returns 401 without authentication", () => {
    cy.request({
      method: "GET",
      url: "/api/persons/" + createdId,
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(401);
    });
  });

  it("GET /api/persons/id returns 200", () => {
    cy.authRequest({
      method: "GET",
      url: "/api/persons/" + createdId
    }).then((response) => {
      expect(response.status).to.eq(200);
    });
  });



  it("PUT /api/persons returns 401 without authentication", () => {
    cy.fixture("maijaKallio").then((person) => {
      cy.request({
        method: "PUT",
        url: "/api/persons/" + createdId,
        failOnStatusCode: false,
        body: person,
      }).then((response) => {
        expect(response.status).to.eq(401);
      });
    });
  });

  it("PUT /api/persons returns 200 with authentication", () => {
    cy.fixture("maijaKallio").then((person) => {
      cy.authRequest({
        method: "PUT",
        url: "/api/persons/" + createdId,
        body: person,
      }).then((response) => {
        expect(response.status).to.eq(200);
      });

      cy.authRequest({
        method: "GET",
        url: "/api/persons/" + createdId,
        body: person,
      }).then((response) => {
        expect(response.status).to.eq(200);

        expect(response.body.birthPlace).to.eq(person.birthPlace);
        expect(response.body.deathPlace).to.eq(person.deathPlace);
      });
    });
  });



  it("DELETE /api/persons/id returns 401 without authentication", () => {
    cy.request({
      method: "DELETE",
      url: "/api/persons/" + createdId,
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(401);
    });
  });

  it("DELETE /api/persons/id returns 204", () => {
    cy.authRequest({
      method: "DELETE",
      url: "/api/persons/" + createdId
    }).then((response) => {
      expect(response.status).to.eq(204);
    });
  });

  it("GET /api/persons/id returns 404 after DELETE request", () => {
    cy.authRequest({
      method: "GET",
      url: "/api/persons/" + createdId,
      failOnStatusCode: false,
    }).then((response) => {
      expect(response.status).to.eq(404);
    });
  });



  it("POST /api/persons returns 400 with invalid parent id", () => {
    cy.fixture("invalidParentId").then((person) => {
      cy.authRequest({
        method: "POST",
        url: "/api/persons",
        failOnStatusCode: false,
        body: person,
      }).then((response) => {
        expect(response.status).to.eq(400);
      });
    });
  });
});