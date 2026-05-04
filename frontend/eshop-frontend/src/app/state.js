// src/app/state.js
/*
UI state contract
=================
state.ui = {
  status: 'READY' | 'LOADING' | 'ERROR',
  errorMessage: null | string,
};
READY -> LOADING -> READY
READY -> ERROR
*/

// FP
// funkce bez vedlejších efektů
// žádná globální proměnná
export function createInitialState() {
  return {
    // ====== domain data =======
    exams: [],
    registrations: [],
    evaluations: [],

    // ====== identity  ======
    auth: { role: 'ANONYMOUS', userId: null, token: null },

    // ====== UI state =========
    ui: {
      mode: 'EXAM_TERM_LIST',
      selectedExamId: null,
      status: 'LOADING',
      errorMessage: null,
      notification: null,
    },
  };
}
