/**
 * 
 */
package com.scholastic.intl.writingawards.helper;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.scholastic.intl.writingawards.entity.City;
import com.scholastic.intl.writingawards.entity.State;
import com.scholastic.intl.writingawards.entity.Task;
import com.scholastic.intl.writingawards.entity.Topic;
import com.scholastic.intl.writingawards.model.CityVO;
import com.scholastic.intl.writingawards.model.ConfigureTaskVO;
import com.scholastic.intl.writingawards.model.StateListVO;
import com.scholastic.intl.writingawards.model.StateVO;
import com.scholastic.intl.writingawards.model.StudentPdfVO;
import com.scholastic.intl.writingawards.model.TopicVO;

/**
 * @author madhusmita.nayak
 * 
 */
@Named
public class ServiceHelper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceHelper.class);
	/**
	 * Set topic entity
	 * 
	 * @param topicVO
	 * @return
	 */
	final List<String> filesListInDir = new ArrayList<String>();

	public Topic setTopicEntity(final TopicVO topicVO) {
		Topic topic = null;
		if (topicVO != null) {
			topic = new Topic();
			topic.setName(topicVO.getTopicName());
			topic.setDescription(topicVO.getTopicDescription());
			topic.setUpdatedBy(topicVO.getCreatedBy());
			topic.setCreatedBy(topicVO.getCreatedBy());
		}
		return topic;
	}

	public Topic setUpdateTopic(final TopicVO topicVO) {
		Topic topic = null;
		if (topicVO != null) {
			topic = new Topic();
			topic.setId(topicVO.getTopicId());
			topic.setName(topicVO.getTopicName());
			topic.setDescription(topicVO.getTopicDescription());
			topic.setUpdatedBy(topicVO.getUpdatedBy());
			topic.setDeleted(true);

		}
		return topic;
	}

	public Task setTaskEntity(final ConfigureTaskVO configureTaskVO) {
		Task taskEntity = null;
		if (configureTaskVO != null) {
			taskEntity = new Task();
			taskEntity.setDescription(configureTaskVO.getTaskName());
			taskEntity.setCreatedBy(configureTaskVO.getCreatedBy());
			taskEntity.setUpdatedBy(configureTaskVO.getCreatedBy());
			taskEntity.setDeleted(false);
			taskEntity
					.setStartDate(stringToDate(configureTaskVO.getStartDate()));
			taskEntity.setEndDate(stringToDate(configureTaskVO.getEndDate()));
		}
		return taskEntity;
	}

	public Task setUpdateTask(final ConfigureTaskVO configureTaskVO) {
		Task taskEntity = null;
		if (configureTaskVO != null) {
			taskEntity = new Task();
			taskEntity.setId(configureTaskVO.getTaskId());
			taskEntity.setUpdatedBy(configureTaskVO.getUpdatedBy());
			taskEntity
					.setStartDate(stringToDate(configureTaskVO.getStartDate()));
			taskEntity.setEndDate(stringToDate(configureTaskVO.getEndDate()));
		}
		return taskEntity;
	}

	public ConfigureTaskVO getTaskVO(final Task task) {
		ConfigureTaskVO configureTaskVO = null;
		if (task != null) {
			configureTaskVO = new ConfigureTaskVO();
			configureTaskVO.setTaskId(task.getId());
			configureTaskVO.setTaskName(task.getDescription());
			configureTaskVO.setCreatedBy(task.getCreatedBy());
			configureTaskVO.setStartDate(dateToString(task.getStartDate()));
			configureTaskVO.setEndDate(dateToString(task.getEndDate()));
			configureTaskVO.setUpdatedBy(task.getUpdatedBy());
		}
		return configureTaskVO;
	}

	private Date stringToDate(final String oldDateString) {
		final String oldFormat = "yyyy-MM-dd";
		final String newFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		final DateFormat formatter = new SimpleDateFormat(oldFormat);
		Date newDate = null;
		try {
			newDate = formatter.parse(oldDateString);
			((SimpleDateFormat) formatter).applyPattern(newFormat);
		} catch (ParseException exception) {
			LOGGER.error("Exception occurs at stringToDate method", exception.getMessage());		}
		return newDate;
	}

	private String dateToString(final Date oldDate) {
		final String oldFormat = "yyyy-MM-dd";
		String oldDateString;
		final DateFormat formatter = new SimpleDateFormat(oldFormat);
		oldDateString = formatter.format(oldDate);
		return oldDateString;
	}

	public StudentPdfVO generateStudentPdfVO(final Object[] stringObject) {
		StudentPdfVO studentPdfVo = null;
		if (stringObject != null) {
			studentPdfVo = new StudentPdfVO();
			studentPdfVo.setStudentName(stringObject[0] == null ? "0"
					: stringObject[0].toString());
			studentPdfVo.setRegistrationNumber(stringObject[1] == null ? "0"
					: stringObject[1].toString());
			studentPdfVo.setSchoolName(stringObject[2] == null ? "0"
					: stringObject[2].toString());
			studentPdfVo.setGroupName(stringObject[3] == null ? "0"
					: stringObject[3].toString());
			studentPdfVo.setStoryTitle(stringObject[4] == null ? "0"
					: stringObject[4].toString());
			studentPdfVo.setStoryText(stringObject[5] == null ? "0"
					: stringObject[5].toString());
		}
		return studentPdfVo;
	}

	private String pdfDateToString(final Date oldDate) {
		final String oldFomrat = "dd MMM yyyy";
		String oldDateString;
		final DateFormat formatter = new SimpleDateFormat(oldFomrat);
		oldDateString = formatter.format(oldDate);
		return oldDateString;
	}

	public File pfdCreateUtility(final String filePath,
			final StudentPdfVO studentPdfVo) {
		final String pfdFileName = studentPdfVo.getRegistrationNumber().concat("_")
				.concat(studentPdfVo.getGroupName()).concat(".pdf");
		final Document document = new Document();
		final File file = new File(filePath + File.separator + pfdFileName);
		try {
			final OutputStream output = new FileOutputStream(file);

			PdfWriter.getInstance(document, output);
			document.open();
			Paragraph paragraph = createStudentParagraph(studentPdfVo);
			document.add(paragraph);

			document.addAuthor("Scholastic India");
			document.addCreationDate();
			document.addCreator("JavaBeat");
			document.addTitle("Student Details");
			document.add(Chunk.NEWLINE);

			final PdfPTable myTable = new PdfPTable(1);
			myTable.setWidthPercentage(100.0f);
			final PdfPCell myCell1 = new PdfPCell(new Paragraph(""));
			myCell1.setBorderWidthTop(2);
			myCell1.setBorderWidthBottom(2);
			myCell1.setBorder(Rectangle.TOP);

			myCell1.setBorderColor(new Color(44, 67, 144));

			myCell1.setHorizontalAlignment(Element.ALIGN_CENTER);

			myCell1.setBorderWidth(2);
			myCell1.setExtraParagraphSpace(2);
			myTable.addCell(myCell1);
			final Paragraph newParagraph = new Paragraph("STORY TEXT", new Font(
					Font.TIMES_ROMAN, 14, Font.BOLD));
			newParagraph.setAlignment(Element.ALIGN_CENTER);
			newParagraph.add(new Paragraph(""));
			final PdfPCell myCell = new PdfPCell(newParagraph);
			myCell.setBorderWidthTop(2);
			myCell.setBorderWidthBottom(2);
			myCell.setBorder(Rectangle.TOP);

			myCell.setBorderColor(new Color(44, 67, 144));
			myCell.setBorder(Rectangle.BOTTOM);

			myCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			myCell.setBorderWidth(2);
			myCell.setExtraParagraphSpace(2);
			myTable.addCell(myCell);
			document.add(myTable);
			final Paragraph storyTitlePar = new Paragraph(
					studentPdfVo.getStoryTitle(), new Font(Font.TIMES_ROMAN,
							14, Font.BOLD));
			storyTitlePar.setAlignment(Element.ALIGN_CENTER);
			storyTitlePar.add(new Paragraph(""));
			document.add(storyTitlePar);

			final Paragraph storytext = new Paragraph(studentPdfVo.getStoryText(),
					new Font(Font.TIMES_ROMAN, 12, Font.NORMAL));
			storytext.add(new Paragraph(""));

			document.add(storytext);

			document.close();
		} catch (DocumentException | FileNotFoundException exception) {
			LOGGER.error("Exception occurs at pfdCreateUtility method", exception.getMessage());
		}
		return file;
	}

	private Paragraph createStudentParagraph(final StudentPdfVO studentPdfVo) {
		final Paragraph info = new Paragraph("", new Font(Font.TIMES_ROMAN, 12,
				Font.BOLD));
		info.add(new Chunk("Student Name: "));
		info.add(new Chunk(studentPdfVo.getStudentName()));
		info.add(new Paragraph(""));
		info.add(new Chunk("Group Name: "));
		info.add(new Chunk(studentPdfVo.getGroupName()));
		info.add(new Paragraph(""));
		info.add(new Chunk("School Name: "));
		info.add(new Chunk(studentPdfVo.getSchoolName()));
		info.add(new Paragraph(""));
		info.add(new Chunk("Registration Number: "));
		info.add(new Chunk(studentPdfVo.getRegistrationNumber()));
		info.add(new Paragraph(""));
		info.add(new Chunk("Topic Name: "));
		info.add(new Chunk(studentPdfVo.getTopicName()));
		info.add(new Paragraph(""));
		info.add(new Chunk("Generated Date: "));
		info.add(new Paragraph(pdfDateToString(new Date())));
		info.add(new Paragraph(""));
		return info;
	}

	public void zipDir(final String directorytoList,
			final ZipOutputStream zipOutputStream) {
		FileInputStream fileOutputStream = null;
		try {

			final File zipDir = new File(directorytoList);
			final String[] dirList = zipDir.list();
			final byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			if (dirList != null) {
				for (int index = 0; index < dirList.length; index++) {
					final File file = new File(dirList[index]);

					fileOutputStream = new FileInputStream(directorytoList
							+ File.separator + file.getPath());
					final ZipEntry anEntry = new ZipEntry(file.getName());
					zipOutputStream.putNextEntry(anEntry);
					while ((bytesIn = fileOutputStream.read(readBuffer)) != -1) {
						zipOutputStream.write(readBuffer, 0, bytesIn);
					}
					zipOutputStream.closeEntry();
					fileOutputStream.close();
				}
			}
		} catch (IOException exception) {
			LOGGER.error("Exception occurs at zipDir method", exception.getMessage());
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException exception) {
					LOGGER.error("Exception occurs at finally block of zipDir method", exception.getMessage());
				}
				fileOutputStream = null;
			}
		}
	}

	/**
	 * Read properties file
	 */
	public Properties readProperties(final String fileName) {
		final Properties prop = new Properties();
		try {
			final String propFileName = fileName;
			final InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(propFileName);
			prop.load(inputStream);
		} catch (IOException exception) {
			LOGGER.error("Exception occurs at finally block of zipDir method", exception.getMessage());
		}
		return prop;
	}

	/**
	 * get all city vo list
	 * 
	 * @param cityList
	 * @return
	 */
	public List<CityVO> getCityVOList(final List<City> cityList) {
		List<CityVO> cityVOList = null;
		if (cityList != null && !cityList.isEmpty()) {
			cityVOList = new ArrayList<CityVO>();
			for (final City cityEntity : cityList) {
				final CityVO cityVO = getCityVO(cityEntity);
				cityVOList.add(cityVO);
			}
		}
		return cityVOList;
	}

	private CityVO getCityVO(final City cityEntity) {
		final CityVO cityVO = new CityVO();
		cityVO.setCityId(cityEntity.getId().longValue());
		cityVO.setCityName(cityEntity.getName());
		return cityVO;
	}

	/**
	 * Get State City List
	 * @param stateList
	 * @return
	 */
	public StateListVO getStateCityVOList(final List<State> stateList) {
		StateListVO stateListVO = null;
		List<StateVO> stateVOList = null;
		if (stateList != null && !stateList.isEmpty()) {
			stateListVO = new StateListVO();
			stateVOList = new ArrayList<StateVO>();
			for (final State stateEntity : stateList) {				
				final StateVO stateVO = getStateVO(stateEntity);
				stateVOList.add(stateVO);
			}
			stateListVO.setStateList(stateVOList);
		}
		return stateListVO;
	}

	private StateVO getStateVO(final State stateEntity) {
		final StateVO stateVO = new StateVO();
		final List<City> cityList = new ArrayList<City>();
		cityList.addAll(stateEntity.getCities());
		stateVO.setStateId(stateEntity.getId().longValue());
		stateVO.setStateName(stateEntity.getName());
		stateVO.setCityList(getCityVOList(cityList));
		return stateVO;
	}
}
