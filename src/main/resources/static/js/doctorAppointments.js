     $(document).ready(function() {
            $('.toggle-button').click(function() {
                $(this).next().toggleClass('hidden');
                $(this).text(function(_, text) {
                    return text === 'See More' ? 'See Less' : 'See More';
                });
            });
        });