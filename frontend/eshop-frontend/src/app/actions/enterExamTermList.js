export function enterExamTermList({ store }) {
  store.setState((state) => ({
    ...state,
    ui: {
      ...state.ui,
      mode: 'EXAM_TERM_LIST',
      selectedExamId: null,
      status: 'READY',
      errorMessage: null,
    },
  }));
}
