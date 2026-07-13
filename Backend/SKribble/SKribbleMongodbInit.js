db = db.getSiblingDB("SKribbleDb");

db.createUser({
    user: "SKribbleUser",
    pwd: "SKribblePassword",
    roles: [
        { role: "readWrite", db: "SKribbleDb" },
        { role: "readWrite", db: "SKribbleTestDb" }
    ]
});
