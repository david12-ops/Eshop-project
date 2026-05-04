/*
 ** viewState má tvar
 ** {
 **   type: 'LOADING' | 'ERROR' | 'EXAM_TERM_LIST' | 'EXAM_TERM_DETAIL' | 'EXAM_TERM_ADMINISTRATION',
 **   message?: string ,
 **   exam?: ExamTerm,
 **   exams?: ExamTerm[],
 **   capabilities?: {
 **     canEnterDetail: boolean,
 **     canEnterAdministration: boolean,
 **     canBackToList: boolean,
 **     canCreateExam: boolean,
 **     canRegister: boolean,
 **     canUnregister: boolean,
 **     canPublish: boolean,
 **     canUnpublish: boolean,
 **     canCancel: boolean,
 **     canDelete: boolean,
 **     canUpdateCapacity: boolean,
 **     canUpdate: boolean
 **   },
 ** }
 */

// zatím jako příklad

export function render(root, state, dispatch) {
    root.replaceChildren();

    const viewState = selectViewState(state);

    // továrna ovladačů
    const handlers = createHandlers(dispatch, viewState);

    let view;

    switch (viewState.type) {
        case 'LOADING':
            view = LoadingView();
            break;

        // kazdy view má props - viewState a handlers, který se pak využívají pro zobrazení a pro obsluhu událostí
        case 'EXAM_TERM_DETAIL':
            if (!viewState.exam) {
                view = ErrorView({ message: 'Zkouškový termín nebyl nalezen.' });
            } else {
                view = ExamTermDetailView({ viewState, handlers });
            }
            break;
        default:
            view = document.createTextNode(`Unknown view type: ${viewState.type}`);
    }

    root.appendChild(view);

    // notifikace (toast)
    const { notification } = state.ui;

    if (notification) {
        const notificationElement = document.createElement('div');
        notificationElement.textContent = notification.message;
        notificationElement.classList.add('notification');
        root.appendChild(notificationElement);
    }
}