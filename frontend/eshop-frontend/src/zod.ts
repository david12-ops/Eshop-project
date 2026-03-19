import { z } from "zod";

const UserSchema = z.object({
    id: z.number(),
    name: z.string(),
    email: z.string().email(),
    age: z.number().min(18)
});

export default UserSchema;

export type User = z.infer<typeof UserSchema>;