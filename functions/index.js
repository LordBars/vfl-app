const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

const isAuthorized = (claims) => (claims === "admin" ||
claims === "superadmin");

const isRoleValid = (role) => [
  "admin",
  "superadmin",
  "student",
  "parent",
  "teacher",
].includes(role);

exports.addUser = functions.https.onCall(async (data, context) => {
  const auth = admin.auth();

  const callerUid = context.auth.uid;
  const callerUser = await auth.getUser(callerUid);
  const callerRole = await callerUser.customClaims.role;

  console.log("User Role:", callerRole);

  if (!isAuthorized(callerRole)) {
    throw new functions.https.HttpsError(
        "Unauthorized Access:", "User is not authenticated as admin");
  }

  const name = data.name;
  const email = data.email;
  const password = data.password;
  const role = data.role;
  const number = data.number || 0;

  if (!isRoleValid(role)) {
    throw new functions.https.HttpsError(
        "Invalid Role", "The given role is not a valid role.");
  }

  const user = await auth.createUser({
    name: name,
    password: password,
    email: email,
    emailVerified: true,
    disabled: false,
  }).catch((error) => console.log("Error when creating new user:", error));

  const uid = user.uid;

  await auth.setCustomUserClaims(uid, {
    role: role,
    number: number,
  });

  return {result: "success"};
});

exports.deleteUser = functions.https.onCall( async (data, context) => {
  const callerUid = context.auth.uid;
  const callerUser = await auth.getUser(callerUid);
  const callerRole = await callerUser.customClaims.role;

  const auth = admin.auth();

  console.log("User Role:", callerRole);

  if (!isAuthorized(callerRole)) {
    throw new functions.https.HttpsError(
        "Unauthorized Access", "User is not authenticated as admin");
  }

  const uids = data.uids;
  if (uids.includes(callerUid)) {
    throw new functions.https.HttpsError(
        "Logic Error", "You can't delete yourself.");
  }

  auth.deleteUsers(uids)
      .then((res) => {
        console.log(`Successfully deleted ${res.successCount} users`);
        console.log(`Failed to delete ${res.failureCount} users`);
        res.forEach((err) => console.log(err.error.toJSON()));
      }).catch((error) => console.log("Error deleting users:", error));

  return {result: "success"};
});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });  */


exports.fetchUsers = functions.https.onCall( async (data, context) => {
  const auth = admin.auth();

  const callerUid = context.auth.uid;
  const callerUser = await auth.getUser(callerUid);
  const callerRole = await callerUser.customClaims.role;

  console.log("User Role:", callerRole);

  if (!isAuthorized(callerRole)) {
    throw new functions.https.HttpsError(
        "Unauthorized Access", "User is not authenticated as admin");
  }

  const users = [];

  const listAllUsers = (nextPageToken) => {
    auth.listUsers(1000, nextPageToken)
        .then((res) => {
          users.push(res.users);
          res.users.forEach((userRecord) => {
            console.log("user", userRecord.toJSON());
          });
          if (res.pageToken) {
            // List next batch of users.
            listAllUsers(res.pageToken);
          }
        }).catch((err) => console.log("Error listing users:", err));
  };

  listAllUsers();

  return {result: users};
});

exports.createSuperAdmin = functions.https.onRequest((req, res) => {
  admin.auth().setCustomUserClaims("YIJjXMXJ7VZsokVmj03S9RqUcRq1", {
    role: 'superadmin'
  });
});
