import { delay } from "./utils.js";
import bcrypt from "bcryptjs";

function generateToken(userId) {
  return `${userId}_${crypto.randomUUID()}`;
}

async function hashPassword(password) {
  return await bcrypt.hash(password, 10);
}

async function isPasswordCorrect(user, password) {
  return await bcrypt.compare(password, user.hashedPassword);
}

export function createAuthApi(db) {
  return {
    async whoAmI(token) {
      await delay();

      if (!token) {
        return {
          status: "REJECTED",
          reason: "Uživatel není přihlášený",
        };
      }

      const user = db.users.find((u) => u.token === token);

      if (!user) {
        return {
          status: "REJECTED",
          reason: "Neplatný token",
        };
      }

      return {
        status: "SUCCESS",
        userId: user.userId,
        role: user.role,
      };
    },

    async register(payload) {
      await delay();

      const { username, password, email, role } = payload;
      if (!username || !password || !email || !role) {
        return {
          status: "REJECTED",
          reason: "Chybí povinné údaje",
        };
      }

      const allowedRoles = [
        "CUSTOMER",
        "ADMIN",
      ];

      if (!allowedRoles.includes(role)) {
        return {
          status: "REJECTED",
          reason: "Neplatná role",
        };
      }

      const existingUser = db.users.find(
        (u) => u.username === username
      );

      if (existingUser) {
        return {
          status: "REJECTED",
          reason: "Uživatel již existuje",
        };
      }

      const user = {
        userId: `user-${crypto.randomUUID()}`,
        username,
        role,
        hashedPassword: await hashPassword(password),
        token: null,
        email,
      };

      db.users.push(user);

      return {
        status: "SUCCESS",
      };
    },

    async login(payload) {
      await delay();

      const { username, password, email } = payload;

      const user = db.users.find(
        (u) => u.username === username && u.email === email
      );

      if (!user) {
        return {
          status: "REJECTED",
          reason: "Uživatel neexistuje",
        };
      }

      const passwordCorrect =
        await isPasswordCorrect(user, password);

      if (!passwordCorrect) {
        return {
          status: "REJECTED",
          reason: "Nesprávné heslo",
        };
      }

      const token = generateToken(user.userId);

      user.token = token;

      return {
        status: "SUCCESS",
        role: user.role,
        userId: user.userId,
        token,
      };
    },

    async logout(token) {
      await delay();

      if (!token) {
        return {
          status: "REJECTED",
          reason: "Token nebyl předán",
        };
      }

      const user = db.users.find(
        (u) => u.token === token
      );

      if (!user) {
        return {
          status: "REJECTED",
          reason: "Neplatný token",
        };
      }

      user.token = null;

      return {
        status: "SUCCESS",
      };
    },
  };
}