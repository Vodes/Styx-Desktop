## Custom CLI arguments
If you ever need to change how MPV is started...

### Important Stuff
- ***%main%*** HAS TO BE THERE - inserts the main `mpv "URL"` command (with language options if enabled)
- Please seperate arguments with quotes (e.g. `"--vo=gpu"`)
- Params before %main% get ignored on Windows because im not quite sure how to implement it yet
- Press Enter to save

### Other parameters
- **%anime%** - inserts a formatted anime name that your filesystem shouldnt complain about
- **%ep%** - inserts the episode number
