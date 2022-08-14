const setSelected = (options, testValue) => options.forEach(opt => {
    if (opt.value === testValue) {
        opt.selected = true;
    }
});
const form = document.querySelector('#search-form');
const categoryOptions = form.querySelectorAll('select[name="category"] option');
const sortOptions = form.querySelectorAll('select[name="sort"] option');
const ascOptions = form.querySelectorAll('select[name="asc"] option');

setSelected(categoryOptions, '@Model.category_id');
setSelected(sortOptions, "${sort}");
setSelected(ascOptions, "${asc}");