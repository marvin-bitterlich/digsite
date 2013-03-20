package utilities;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipArchiveExtractor {

	public void extractArchive(File archive, File destDir) throws Exception {
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		ZipFile zipFile = new ZipFile(archive);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		byte[] buffer = new byte[16384];
		int len;
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();

			String entryFileName = entry.getName();

			File dir = buildDirectoryHierarchyFor(entryFileName, destDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			if (!entry.isDirectory()) {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(new File(destDir, entryFileName)));

				BufferedInputStream bis = new BufferedInputStream(
						zipFile.getInputStream(entry));

				while ((len = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}

				bos.flush();
				bos.close();
				bis.close();
			}
		}
		zipFile.close();
	}

	private File buildDirectoryHierarchyFor(String entryName, File destDir) {
		int lastIndex = entryName.lastIndexOf('/');
		// String entryFileName = entryName.substring(lastIndex + 1);
		String internalPathToEntry = entryName.substring(0, lastIndex + 1);
		return new File(destDir, internalPathToEntry);
	}

	public static void extractArchiveFromRec(String fName) {
		try {
			byte[] buf = new byte[1024];
			ZipInputStream zinstream = new ZipInputStream(new FileInputStream(
					fName));
			ZipEntry zentry;

			zentry = zinstream.getNextEntry();

			System.out.println("Name of current Zip Entry : " + zentry + "\n");
			while (zentry != null) {
				String entryName = zentry.getName();
				System.out.println("Name of  Zip Entry : " + entryName);
				FileOutputStream outstream = new FileOutputStream(entryName);
				int n;

				while ((n = zinstream.read(buf, 0, 1024)) > -1) {
					outstream.write(buf, 0, n);

				}
				System.out.println("Successfully Extracted File Name : "
						+ entryName);
				outstream.close();

				zinstream.closeEntry();
				zentry = zinstream.getNextEntry();
			}
			zinstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}