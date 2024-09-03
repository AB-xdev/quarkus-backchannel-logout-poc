# Quarkus Backchannel Logout Bug PoC

This is a minimalistic PoC of a project. 
All sensitive information inside this repo has been removed beforehand.

## Reproduction guide

### Prepare Keycloak

1. Start the Keycloak. Open ``docker-dev`` and run ``docker compose up`` (to destroy the infrastructure later: Abort with ``Ctrl+C`` and run ``docker compose down``
2. Open http://localhost:8899/
3. Login using the user ``admin`` and the password ``admin``
4. Open http://localhost:8899/admin/master/console/#/wim/clients/915c2aa9-9447-469c-a5c6-6a222e217d2f/settings to see all settings of the ``quarkus`` client inside the ``wim`` realm
5. Open http://localhost:8899/admin/master/console/#/wim/clients/915c2aa9-9447-469c-a5c6-6a222e217d2f/sessions to get a look over all active sessions for the client
6. Leave this window open

### 1. Showacsing the incorrect routing and getting any feedback from Quarkus

Setup:
* [application.properties](./backend-rest/src/main/resources/application.properties): ``quarkus.oidc.wim.logout.backchannel.path`` is set to ``/oidc/wim/back-channel-logout``
* [win-realm.json](./docker-dev/realm-import/win-realm.json): ``backchannel.logout.url`` is set to ``http://host.docker.internal:8080/backend/backend/oidc/wim/back-channel-logout`` (contains ``backend`` twice!)

1. Start Quarkus inside ``backend-dev``. You can use the pre-definied IntelliJ IDEA Launcher called ``Run Backend (dev)``
2. Inside a **new private tab** open http://localhost:8080/backend/v1/auth/wim/callback
3. You will get redirect to keycloak. Login using the user ``test`` and the password ``test``
4. You should be redirected, the site says ``YOU_ARE_LOGGED_IN``
5. Switch to the other browser window where the active sessions for the client are displayed, refresh the tab, it should now contain a session for the user ``test``
6. On the "..."-menu at the end of the row click "Sign out"

❌ The quarkus logs now read:
```
2024-09-03 15:46:30,252 DEBUG [io.qua.oid.run.BackChannelLogoutHandler] (vert.x-eventloop-thread-3) Back channel logout request for the tenant wim received
2024-09-03 15:46:30,253 ERROR [io.qua.oid.run.BackChannelLogoutHandler] (vert.x-eventloop-thread-3) Tenant configuration for the tenant wim is not available or does not match the backchannel logout path
```

### 2. Showcasing how it should be configured

Change the following:
1. ``backchannel.logout.url`` inside keycloak should be: ``http://host.docker.internal:8080/backend/oidc/wim/back-channel-logout``
  * You can change this in the UI at http://localhost:8899/admin/master/console/#/wim/clients/915c2aa9-9447-469c-a5c6-6a222e217d2f/settings
  * Then click "Save"
2. Do the same as above inside "1. Showacsing the incorrect routing..."

❌ Notice how this time there is no log output at all
❌ The request is still received by Quarkus (e.g. Breakpoint inside ``RouterImpl#handleContext`` triggers) but not processed
