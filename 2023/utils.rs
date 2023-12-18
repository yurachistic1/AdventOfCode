use std::fs::File;
use std::io::{BufRead, BufReader};

pub fn read_input(file_path: &str) -> Vec<String> {
    // Open the file
    let file = File::open(file_path).unwrap();

    // Create a buffered reader
    let reader = BufReader::new(file);

    reader
        .lines()
        .filter_map(Result::ok)
        .map(|line| line.trim().to_string())
        .filter(|line| !line.is_empty())
        .collect()
}

pub fn get_adjacent_8(x: usize, y: usize, x_bound: usize, y_bound: usize) -> Vec<(usize, usize)> {
    let mut res: Vec<(usize, usize)> = vec![(x + 1, y), (x + 1, y + 1), (x, y + 1)];

    if x > 0 {
        res.push((x - 1, y));
        res.push((x - 1, y + 1));
    }

    if y > 0 {
        res.push((x, y - 1));
        res.push((x + 1, y - 1));
    }

    if x > 0 && y > 0 {
        res.push((x - 1, y - 1))
    }

    res.iter()
        .filter(|(a, b)| a < &x_bound && b < &y_bound)
        .cloned()
        .collect()
}