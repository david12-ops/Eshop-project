export function createCategoryApi(db) {
    return {
        async getCategories() {
            return {
                status: "SUCCESS",
                categories: db.categories,
            };
        },

        async getCategory(id) {
            const category = db.categories.find(
                (c) => c.id === id
            );

            if (!category) {
                return {
                    status: "ERROR",
                    reason: "Category not found",
                };
            }

            return {
                status: "SUCCESS",
                category,
            };
        },
    };
}